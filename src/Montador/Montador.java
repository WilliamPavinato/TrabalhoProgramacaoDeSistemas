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
    public Output output = new Output();
    ArrayList<Line> intermediateFile = new ArrayList<>();

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

    // Cria a tabela de simbolos
    private void passoUm() {
        int LocationCounter = 0; // endereço atual

        output.startingAddress = LocationCounter;

        for(String linha : input)
        {
            Line line = new Line();
            line.parser(linha);

            if (linha.isEmpty() || linha.charAt(0) == '.')
                continue;       // pula linhas que começam com . (comentários)

            String label          = getLabel(linha);
            String opcode         = getOpcode(linha);
            List<String> operands = getOperands(linha);

            if(label != null)
                SYMTAB.put(label, LocationCounter);

            if (OPTAB.getInstrucaoPorNome(opcode) != null) { // instruction
                LocationCounter++;

                int tamanhoIntrucao = OPTAB.getInstrucaoPorNome(opcode).getLength();
                switch (tamanhoIntrucao)
                {
                    case 3:
                        if(line.extended)
                        {
                            LocationCounter += 4;
                            line.set_tamanho_instr(4);
                        }
                        else
                        {
                            LocationCounter += 3;
                            line.set_tamanho_instr(3);
                        }
                        break;
                    default:
                        LocationCounter += tamanhoIntrucao;
                        line.set_tamanho_instr(tamanhoIntrucao);
                        break;
                }

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
                        line.set_tamanho_instr(1);
                        LocationCounter++;
                        break;
                    case "WORD":
                        line.set_tamanho_instr(3);
                        LocationCounter++;
                        break;
                    case "BYTE":
                        LocationCounter++;
                        line.set_tamanho_instr(1);
                        break;
                    case "RESW":
                        int aux = Integer.parseInt(line.operands[0]);
                        LocationCounter = LocationCounter + (3*aux);
                        line.set_tamanho_instr(3*aux);
                    case "RESB":
                        assert operands != null;
                        aux = 0;
                        for (String operand : operands)
                            aux += Integer.parseInt(operand);
                        LocationCounter += aux;
                        line.set_tamanho_instr(aux);
                        break;
                    default:
                        errorMsg = errorMsg + "\nERRO - Instrucao invalida: " + linha;
                        break;
                }
            }
            line = intermediateFile.get(LocationCounter);
        }

        output.endAddress = LocationCounter;
        output.setLength();
    }

    // Gera código de máquina e arquivo temporário a partir da tabela de símbolos
    private void passoDois() {
        int lineCounter = 0;
        int LOCCTR = 0;

        String obj = "";

        Line line = intermediateFile.get(lineCounter);

        if ( line.opcode.equals("START") )
        {
            LOCCTR = Integer.parseInt(line.operands[0]);
            lineCounter +=1;
        }
        else
        {
            LOCCTR = 0;
        }

        line = intermediateFile.get(lineCounter);

        while ( !line.opcode.equals("END") )
        {
            if( OPTAB.getInstrucaoPorNome(line.opcode) != null )
            {
                LOCCTR += line.tamanho_instr;

                // Nao possuimos instruçoes formato 1 (Alem de RD e WD que são tratadas a parte)

                if(line.tamanho_instr == 2)
                {
                    obj = montarF2(line);
                    output.machineCode.add(hexToBinary(obj));
                }

                else if(line.tamanho_instr > 2)
                {
                    obj = montarF3F4(line,LOCCTR);
                    output.machineCode.add(hexToBinary(obj));
                }
            }
            else if (line.opcode.equals("RD"))
            {
                LOCCTR +=1;
                output.machineCode.add("11011000");
            }
            else if (line.opcode.equals("WD"))
            {
                LOCCTR +=1;
                output.machineCode.add("11011100");
            }
            else if(line.opcode.equals("BYTE"))
            {
                LOCCTR +=1;
                char c = line.operands[0].charAt(0);

                if (c == 'C') // ASCII ex: C'EOF'
                {
                    String aux = line.operands[0].substring(2,line.operands[0].length()-1);
                    for(int i=0; i < aux.length(); i++)
                    {
                        obj = String.format("%1$02X",(int)aux.charAt(i) & 0xFF);
                    }
                } else if (c == 'X') // Hexadecimal ex: X'05'
                {
                    obj = line.operands[0].substring(2,line.operands[0].length()-1);
                } else // Numero ex: 5
                {
                    obj = line.operands[0];
                }

                output.machineCode.add(hexToBinary(obj));

            }
            else if(line.opcode.equals("WORD"))
            {
                LOCCTR +=3;
                int word = Integer.parseInt(line.operands[0]);
                obj = String.format("%1$06X",word & 0xFFFFFF);
                output.machineCode.add(hexToBinary(obj));
            }
            else if(line.opcode.equals("RESW"))
            {
                LOCCTR += line.tamanho_instr;
                int numero_palavras = (line.tamanho_instr)/3;

                for(int i=0; i < numero_palavras; i++)
                {
                    obj = String.format("%1$06X",0x0 & 0xFFFFFF);
                    output.machineCode.add(hexToBinary(obj));
                }
            }
            else if(line.opcode.equals("RESB"))
            {
                LOCCTR += line.tamanho_instr;
                int numero_bytes = line.tamanho_instr;

                for(int i=0; i < numero_bytes;i++)
                {
                    obj = String.format("%1$02X",0x0 & 0xFF);
                    output.machineCode.add(hexToBinary(obj));
                }
            }
            else
            {
                errorMsg = errorMsg + "\nERRO - Opcode Inválido: " + input.get(lineCounter);
            }

            lineCounter+=1;
            line = intermediateFile.get(lineCounter);
        }

        output.endAddress = LOCCTR;
        output.setLength();
    }

    private void gerarTXTOutput() {
        try (var fileWriter = new FileWriter(System.getProperty("user.dir") + "/ArquivosTXT/outputMontador.txt")) {
            fileWriter.write(String.join("\n", output.machineCode));
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

    //Monta instrucao do formato 2
    public String montarF2(Line line){

        String opCode = String.format("%1$02X", OPTAB.getInstrucaoPorNome(line.opcode).getOpcode()& 0xFF);
        String operando1 = line.operands[0];
        String operando2 = line.operands[1];

        String r1 = "0";
        String r2 = "0";

        if( SYMTAB.containsKey(operando1) )
        {
            r1 = String.format("%1$01X", SYMTAB.get(operando1) & 0xF);
        }
        else
        {
            r1 = operando1;
        }

        if( SYMTAB.containsKey(operando2) )
        {
            r2 = String.format("%1$01X", SYMTAB.get(operando2) & 0xF);
        }
        else
        {
            r2 = operando2;
        }

        return opCode + r1 + r2;
    }

    //Monta instrucao do formato 3 ou formato 4
    public String montarF3F4(Line line, int PC)
    {
        byte opcode = OPTAB.getInstrucaoPorNome(line.opcode).getOpcode();
        int operand = 0;

        int ni = 0;
        int xbpe = 0;
        int disp = 0;

        int obj = 0;

        String firstByte = "";
        String hexAddress = "";

        if( line.prefix.isEmpty() ){
            ni = 0x03;
        }
        else if( line.prefix.equals("#") ) {
            ni = 0x01;
        }
        else if( line.prefix.equals("@") ) {
            ni = 0x02;
        }
        else {
            errorMsg = errorMsg + "\nERRO - Prefixo inválido: " + line.line;
        }


        if( !SYMTAB.containsKey(line.operands[0]) ) // Constante
        {
            try {
                disp = Integer.parseInt(line.operands[0]);

            } catch (NumberFormatException e) {
                errorMsg = errorMsg + "\nERRO - Nao foi possivel converter para inteiro: " + line.line;
            }

            xbpe = 0;
            obj = ((opcode & 0xFC) <<16) + (ni<< 16) + (xbpe << 12)+ disp;

            firstByte = String.format("%1$02X", (opcode + ni) & 0xFF);
            hexAddress = String.format("%1$04X",obj & 0xFFFF);
        }
        else if( line.extended == true ) // Formato 4
        {
            operand = SYMTAB.get(line.operands[0]);
            xbpe = 0x01;
            disp = operand;
            obj = ((opcode & 0xFC) <<24) + (ni<< 24) + (xbpe << 20)+ disp;

            firstByte = String.format("%1$02X", (opcode + ni) & 0xFF);
            hexAddress = String.format("%1$04X",obj & 0xFFFF);
        }
        else // Formato 3
        {
            operand = SYMTAB.get(line.operands[0]);

            if(line.operands[1].equals("X")) // Indexado
            {
                disp = operand - PC + SYMTAB.get("X");
                xbpe = 0xA;
            }
            else
            {
                disp = operand - PC;
                xbpe = 0x2;
            }

            String string_disp = "";

            if(disp < 0)
            {
                string_disp = String.format("%1$01X", disp & 0xFFF);
            }
            else if(disp >=2048)
            {
                ni = 0x0;
                disp +=PC;

                firstByte = String.format("%1$02X", (opcode + ni) & 0xFF);
                hexAddress = String.format("%1$04X", disp & 0x7FFF);
            }
            else
            {
                string_disp = String.format("%1$03X", disp & 0xFFF);
            }

            firstByte = String.format("%1$02X", (opcode + ni) & 0xFF);
            hexAddress = String.format("%1$01X", xbpe & 0xF) + string_disp;
        }

        return firstByte + hexAddress;
    }

    String hexToBinary(String hex)
    {
        String binary = "";

        hex = hex.toUpperCase();

        HashMap<Character, String> hashMap = new HashMap<Character, String>();

        hashMap.put('0', "0000");
        hashMap.put('1', "0001");
        hashMap.put('2', "0010");
        hashMap.put('3', "0011");
        hashMap.put('4', "0100");
        hashMap.put('5', "0101");
        hashMap.put('6', "0110");
        hashMap.put('7', "0111");
        hashMap.put('8', "1000");
        hashMap.put('9', "1001");
        hashMap.put('A', "1010");
        hashMap.put('B', "1011");
        hashMap.put('C', "1100");
        hashMap.put('D', "1101");
        hashMap.put('E', "1110");
        hashMap.put('F', "1111");

        int i;
        char ch;

        for (i = 0; i < hex.length(); i++) {
            ch = hex.charAt(i);
            if (hashMap.containsKey(ch))
                binary += hashMap.get(ch);
            else {
                binary = "Invalid Hexadecimal String";
                return binary;
            }
        }
        return binary;
    }
}