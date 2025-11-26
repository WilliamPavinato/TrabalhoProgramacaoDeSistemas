package Montador;

import Instrucoes.Instructions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

public class Montador {
    private String errorMsg = "";
    private final Instructions instrucoes     = new Instructions();
    private final Map<String, Integer> SYMTAB = new HashMap<>();

    List<String> input  = new ArrayList<>();
    List<String> output = new ArrayList<>();

    public void montarPrograma(String caminho)
    {
        setPrograma(caminho);
        passoUm();
        passoDois();
        gerarTXTOutput();
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Arquivo de entrada: ")
                .append(System.getProperty("user.dir"))
                .append("\\ArquivosTXT\\inputMontador.txt\n");
        mensagem.append("Arquivo de saída: ")
                .append(System.getProperty("user.dir"))
                .append("\\ArquivosTXT\\outputMontador.txt\n");

        if (errorMsg.isEmpty())
            mensagem.append("Programa montado com sucesso.");
        else
            mensagem.append("Programa montado com erros. Erro(s): \n").append(errorMsg);

        JOptionPane.showMessageDialog(null, mensagem, "Montador", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setPrograma(String caminho) {
        File file = new File(caminho);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = br.readLine()) != null){
                input.add(str);
            }
        }
        catch(Exception e) {errorMsg = errorMsg + "\nErro ao ler o arquivo de entrada.";}
    }

    private void passoUm() {
        int LocationCounter = 0; // endereço atual

        for(String linha : input)
        {
            if (linha.isEmpty())
                continue;

            String label          = getLabel(linha);
            String opcode         = getOpcode(linha);
            List<String> operands = getOperands(linha);

            if(label != null)
                SYMTAB.put(label, LocationCounter);

            if (instrucoes.getInstrucaoPorNome(opcode) != null) { // instruction
                LocationCounter++;

                assert operands != null;
                for (String operand : operands) {
                    if (!isNumeric(operand))
                        SYMTAB.putIfAbsent(operand, null); // referenciado, mas ainda não definido

                    LocationCounter++;
                }
            }
            else { // pseudo-instruction
                switch (Objects.requireNonNull( opcode )) {
                    case "RD":
                    case "WD":
                    case "WORD":
                    case "BYTE":
                        LocationCounter++;
                        break;
                    case "RESW":
                    case "RESB":
                        assert operands != null;
                        for (String operand : operands)
                            LocationCounter += Integer.parseInt(operand);
                        break;
                    default:
                        errorMsg = errorMsg + "\nERRO - Instrucao invalida: " + linha;
                        break;
                }
            }
        }
    }

    private void passoDois()
    {
        for(String linha : input)
        {
            if (linha.isEmpty())
                continue;

            String opcode         = getOpcode(linha);
            List<String> operands = getOperands(linha);

            if (instrucoes.getInstrucaoPorNome(opcode) != null) // Instrucao
            {
                output.add(instrucoes.getInstrucaoPorNome(opcode).getOpcode());

                assert operands != null;
                for (String operand : operands)
                {
                    if (isNumeric(operand))
                        output.add(Integer.toHexString(Integer.parseInt(operand)).toUpperCase());
                    else
                    if (SYMTAB.get(operand) == null)
                    {
                        output.add(Integer.toHexString(0).toUpperCase());
                        errorMsg = errorMsg + "\nERRO - Label nao definida: " + linha;
                    }
                    else
                        output.add(Integer.toHexString(SYMTAB.get(operand)).toUpperCase());
                }
            }
            else // pseudo-instruction
            {
                switch (Objects.requireNonNull( opcode )) {
                    case "RD":
                        output.add("D8");
                        break;
                    case "WD":
                        output.add("DC");
                        break;
                    case "WORD":
                    case "BYTE":
                        assert operands != null;
                        for (String operand : operands)
                            output.add(Integer.toHexString(Integer.parseInt(operand)).toUpperCase());
                        break;
                    case "RESW":
                    case "RESB":
                        assert operands != null;
                        for (String operand : operands)
                            for (int i = 0; i < Integer.parseInt(operand); i++)
                                output.add("XX");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void gerarTXTOutput()
    {
        try (var fileWriter = new FileWriter(System.getProperty("user.dir") + "\\ArquivosTXT\\outputMontador.txt")) {
            for (String str : output) {
                fileWriter.write(str + System.lineSeparator());
            }
        }
        catch (IOException e) {e.printStackTrace();}
    }

    private String getLabel(String linha) {
        String[] splitted = linha.split("\\s+");
        try {
            if (instrucoes.getInstrucaoPorNome(splitted[0]) == null) // tem label
                return splitted[0];
            else // não tem label
                return null;
        }
        catch (Exception e) {return null;}
    }

    private String getOpcode(String linha) {
        String[] splitted = linha.split("\\s+");
        try {
        //                                                                 tem label     não tem label
            return (instrucoes.getInstrucaoPorNome(splitted[0]) == null) ? splitted[1] : splitted[0];
        }
        catch (Exception e) {return null;}
    }

    private List<String> getOperands(String linha) {
        String[] splited      = linha.split("\\s+");
        List<String> operands = new ArrayList<>();

        try {
            if (instrucoes.getInstrucaoPorNome(splited[0]) == null){ // tem label
                operands.addAll(Arrays.asList(splited).subList(2, splited.length));
            }
            else{ // não tem label
                operands.addAll(Arrays.asList(splited).subList(1, splited.length));
            }
            return operands;
        }
        catch (Exception e) {return null;}
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {return false;}

        try {Double.parseDouble(strNum);}
        catch (NumberFormatException nfe) {return false;}

        return true;
    }
}
