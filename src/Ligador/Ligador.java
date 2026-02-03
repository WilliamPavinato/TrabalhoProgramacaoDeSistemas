package Ligador;

import Montador.*;
import java.util.ArrayList;
import java.util.List;

import Instrucoes.Instructions;



public class Ligador {
    private ArrayList<Montador> programas  = new ArrayList<>(); // Lista de programas a serem ligados
    List<Object[]> tabelaDeSimbolosGlobal = new ArrayList<Object[]>();// Tabela de símbolos global
    private Output output = new Output();
    private Instructions instrucoes = new Instructions();

    public void limpar()
    {
        programas.clear();
        tabelaDeSimbolosGlobal.clear();
        output.machineCode.clear();
    }

    public void adicionarPrograma(Montador montador) {
        programas.add(montador);
    }

    public String ligarProgramas() {

        primeiraPassagem();
        segundaPassagem();
        return output.getMachineCodeAsString();
    }

    private void primeiraPassagem() {

        // Adiciona os símbolos do programa 1 à tabela de símbolos global
        for (Object[] entrada : programas.get(0).getDEFTAB())
        {
            tabelaDeSimbolosGlobal.add(entrada);
        }

        // Adiciona os símbolos do programa 2 à tabela de símbolos global, somando o tamanho do programa 1

        if(programas.size() > 1) {
            for (Object[] entrada : programas.get(1).getDEFTAB()) {
                entrada[1] = (int) entrada[1] + programas.get(0).getOutput().getLength();
                tabelaDeSimbolosGlobal.add(entrada);
            }
        }

        // Atualiza a tabela de uso do programa 2, adicionando o tamanho do programa 1
        for (Object[] entrada : programas.get(1).getUSETAB())
        {
            entrada[1] = (int)entrada[1] + programas.get(0).getOutput().getLength();
        }
    }

    private void segundaPassagem() {
        // Adiciona o código do programa 1 ao código de saída
        for (String codigo : programas.get(0).getOutput().machineCode)
        {
            output.machineCode.add(codigo);
        }

        // Adiciona o código do programa 2 ao código de saída
        if (programas.size() > 1) {
            for (String codigo : programas.get(1).getOutput().machineCode) {
                output.machineCode.add(codigo);
            }
        }
        //Percorre o código de saída atualizando os deslocamentos
        int loc = 0;
        for (int i = 0; i < output.machineCode.size(); i++)
        {
            // Verificar Tabela de Uso programa 1
            for (Object[] entrada : programas.get(0).getUSETAB())
            {
                if (loc == (int)entrada[1])
                {
                    String simbolo = (String)entrada[0];
                    int valor = 0;

                    for (Object[] entrada2 : tabelaDeSimbolosGlobal)
                    {
                        if (entrada2[0].equals(simbolo))
                        {
                            valor = (int)entrada2[1];
                        }
                    }

                    String disp = Integer.toBinaryString(valor);
                    disp = String.format("%12s", disp).replaceAll(" ", "0");

                    String codigoAtualizado = output.machineCode.get(i).substring(0, 12) + disp;
                    output.machineCode.set(i, codigoAtualizado);
                }
            }

            // Verificar Tabela de Uso programa 2

            if(programas.size() > 1){
                for (Object[] entrada : programas.get(1).getUSETAB())
                {
                    if (loc == (int)entrada[1])
                    {
                        String simbolo = (String)entrada[0];
                        int valor = 0;

                        for (Object[] entrada2 : tabelaDeSimbolosGlobal)
                        {
                            if (entrada2[0].equals(simbolo))
                            {
                                valor = (int)entrada2[1];
                            }
                        }

                        String disp = Integer.toBinaryString(valor);
                        disp = String.format("%12s", disp).replaceAll(" ", "0");

                        String codigoAtualizado = output.machineCode.get(i).substring(0, 12) + disp;
                        output.machineCode.set(i, codigoAtualizado);
                    }
                }
            }

            // Pegar primeiros 8 bits para pegar o opcode
            String opcodeString = output.machineCode.get(i).substring(0, 8);
            Byte opcode = (byte) ((byte)Integer.parseInt(opcodeString, 2) & 0b11111100);

            if (opcode == (byte)0xD8 || opcode == (byte)0xD) // RD e WD
            {
                loc+=1;
            }
            else if (instrucoes.getInstrucao(opcode) == null)   // Se a inrtucao nao existe é BYTE, WORD
            {
                if (output.machineCode.get(i).length() > 8)     // WORD
                    loc+=3;
                else                                            // BYTE
                    loc+=1;
            }
            else
            {
                loc += instrucoes.getInstrucao(opcode).getLength();
            }
        }

    }

}