package Montador;

import Instrucoes.Instructions;
import Executor.Registradores;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

public class Montador {
    private String errorMsg = "";
                                                    //  TABELAS
    private final Instructions OPTAB;               // Instruções
    private final Map<String, String> POPTAB;       // Pseudo-instruções
    private final Map<String, Integer> SYMTAB;      // Símbolos
    private final List<String> input = new ArrayList<>();
    private final List<String> output = new ArrayList<>();

    public Montador() {
        OPTAB = new Instructions();

        POPTAB = new HashMap<>();
        POPTAB.put("RD",   "D8");
        POPTAB.put("WD",   "DC");
        POPTAB.put("WORD", null);
        POPTAB.put("BYTE", null);
        POPTAB.put("RESW", "0");
        POPTAB.put("RESB", "0");

        SYMTAB = new HashMap<>();
    }

    public void montarPrograma(String caminho) {
        setPrograma(caminho);
        passoUm();
        passoDois();
        gerarTXTOutput();
        mostrarMensagem();
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
            if (linha.isEmpty() || linha.charAt(0) == '.')
                continue;       // pula linhas que começam com . (comentários)

            String label          = getLabel(linha);
            String opcode         = getOpcode(linha);
            List<String> operands = getOperands(linha);

            if(label != null)
                SYMTAB.put(label, LocationCounter);

            if (OPTAB.getInstrucaoPorNome(opcode) != null) { // instruction
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

    private void passoDois() {
        for(String linha : input) {
            if (linha.isEmpty() || linha.charAt(0) == '.')
                continue;       // pula linhas que começam com . (comentários)

            String opcode         = getOpcode(linha);
            List<String> operands = getOperands(linha);

            if (OPTAB.getInstrucaoPorNome(opcode) != null) { // Instrucao
                output.add(OPTAB.getInstrucaoPorNome(opcode).getOpcode());

                assert operands != null;
                for (String operand : operands) {
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
            else { // pseudo-instruction
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
                                output.add("0");
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void gerarTXTOutput() {
        try (var fileWriter = new FileWriter(System.getProperty("user.dir") + "/ArquivosTXT/outputMontador.txt")) {
            for (String str : output) {
                fileWriter.write(str + System.lineSeparator());
            }
        }
        catch (IOException e) {e.printStackTrace();}
    }

    private void mostrarMensagem() {
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

    private String getLabel(String linha) {
        String[] splitted = linha.split("\\s+");
        try {
            if ((OPTAB.getInstrucaoPorNome(splitted[0]) != null) ||     // tem label
            (POPTAB.get(splitted[0]) != null)) {
                return null;
            }
            else { return splitted[0]; } // tem label
        }
        catch (Exception e) {return null;}
    }

    private String getOpcode(String linha) {
        String[] splitted = linha.split("\\s+");
        try {
            if ((OPTAB.getInstrucaoPorNome(splitted[0]) != null) ||     // não tem label
            (POPTAB.get(splitted[0]) != null )) {
                return splitted[0];
            }
            else { return splitted[1]; }    // tem label
        }
        catch (Exception e) { return null; }
    }

    private List<String> getOperands(String linha) {
        String[] splitted      = linha.split("\\s+");
        List<String> operands  = new ArrayList<>();

        try {
            if ((OPTAB.getInstrucaoPorNome(splitted[0]) != null) ||      // não tem label
            ( POPTAB.get(splitted[0]) != null ) ) {
                // Separar operandos por vírgula
                splitted = splitted[1].split(",");
                for (String s : splitted)
                    if (Registradores.getChaveRegistradorPorNome(s) != -1) {
                        operands.add(Integer.toString(Registradores.getChaveRegistradorPorNome(s)));
                    } else { operands.add(s); }
            }
            else {  // não tem label
                // Separar operandos por vírgula
                splitted = splitted[2].split(",");
                for (String s : splitted)
                    if (Registradores.getChaveRegistradorPorNome(s) != -1) {
                        operands.add(Integer.toString(Registradores.getChaveRegistradorPorNome(s)));
                    } else { operands.add(s); }
            }

            for(int i = 0; i < operands.size(); i++) {
                String operand = operands.get(i);

                if (operand.length() > 1) {              // Se for >1 caracteres, é um nome e deve ser convertido pra chave
                    operands.set(i, Integer.toString(Registradores.getChaveRegistradorPorNome(operand)));
                } else if (operand.charAt(0) > '9') {    // Compara valor ASCII: se <=0 é algum número e se >0 é uma letra
                    operands.set(i, Integer.toString(Registradores.getChaveRegistradorPorNome(operand)));
                }
            }

            return operands;
        }
        catch (Exception e) {return null;}
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {return false;}

        try { Double.parseDouble(strNum); }
        catch (NumberFormatException nfe) { return false; }

        return true;
    }
}