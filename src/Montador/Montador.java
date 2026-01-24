package Montador;

import Instrucoes.Instructions;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

public class Montador {
    private String errorMsg = "";

    private final Instructions OPTAB;               // Instruções
    private final Map<String, Integer> SYMTAB;      // Símbolos

    private final ArrayList<String> input = new ArrayList<>();
    public Output output                  = new Output();

    ArrayList<Line> intermediateFile = new ArrayList<>();

    public Montador() {
        OPTAB = new Instructions();

        SYMTAB = new HashMap<>();
        SYMTAB.put("A",  0);
        SYMTAB.put("X",  1);
        SYMTAB.put("L",  2);
        SYMTAB.put("B",  3);
        SYMTAB.put("S",  4);
        SYMTAB.put("T",  5);
        SYMTAB.put("PC", 8);
        SYMTAB.put("SW", 9);
    }

    public String Montar(String codigoAssembly) {
        limpaListas();
        setPrograma(codigoAssembly);
        passoUm();
        passoDois();
        gerarTXTOutput();
        mostrarMensagem();

        return String.join("\n", output.machineCode);
    }

    public void setPrograma(String codigoAssembly) {
        String[] linhas = codigoAssembly.split("\\r?\\n");
        Collections.addAll(input, linhas);
    }

    public void limpaListas() {
        input.clear();
        output.reset();
        SYMTAB.clear();
        intermediateFile.clear();
        errorMsg = "";
        SYMTAB.put("A", 0);
        SYMTAB.put("X", 1);
        SYMTAB.put("L", 2);
        SYMTAB.put("B", 3);
        SYMTAB.put("S", 4);
        SYMTAB.put("T", 5);
        SYMTAB.put("PC", 8);
        SYMTAB.put("SW", 9);
    }

    // Cria a tabela de simbolos
    public void passoUm() {
        int lineCounter = 0;
        int LOCCTR;

        Line line = new Line();
        line.parser(input.get(lineCounter));

        if(line.opcode.equals("START"))
        {
            LOCCTR = Integer.parseInt(line.operands[0]);
            intermediateFile.add(line);

            lineCounter +=1;
            line = new Line();
            line.parser(input.get(lineCounter));
        }
        else
        {
            LOCCTR = 0;
        }

        output.startingAddress = LOCCTR;

        while( !(line.opcode.equals("END")) )
        {
            line.setAddress(LOCCTR);

            if( !(line.label.isEmpty()) )
            {
                if( SYMTAB.containsKey(line.label) )
                {
                    errorMsg = errorMsg + "\nERRO - Multipla definição: " + input.get(lineCounter);
                }
                else
                {
                    SYMTAB.put(line.label,LOCCTR);
                }
            }

            if( OPTAB.getInstrucaoPorNome(line.opcode) != null )
            {
                int tamanhoIntrucao = OPTAB.getInstrucaoPorNome(line.opcode).getLength();
                if (tamanhoIntrucao == 3) {
                    if (line.extended) {
                        LOCCTR += 4;
                        line.set_tamanho_instr(4);
                    } else {
                        LOCCTR += 3;
                        line.set_tamanho_instr(3);
                    }
                } else {
                    LOCCTR += tamanhoIntrucao;
                    line.set_tamanho_instr(tamanhoIntrucao);
                }
            }
            else if (line.opcode.equals("RD") || line.opcode.equals("WD"))
            {
                LOCCTR +=1;
                line.set_tamanho_instr(1);
            }
            else if (line.opcode.equals("WORD"))
            {
                LOCCTR +=3;
                line.set_tamanho_instr(3);
            }
            else if(line.opcode.equals("RESW"))
            {
                int aux = Integer.parseInt(line.operands[0]);
                LOCCTR = LOCCTR + (3*aux);
                line.set_tamanho_instr(3*aux);
            }
            else if(line.opcode.equals("RESB"))
            {
                int aux = Integer.parseInt(line.operands[0]);
                LOCCTR += aux;
                line.set_tamanho_instr(aux);
            }
            else if(line.opcode.equals("BYTE"))
            {
                char tipo = line.operands[0].charAt(0);
                int tamanho = 0;

                // lida com diferentes tamanhos da instrucao BYTE
                if (tipo == 'C') {
                    // C'EOF' 3 bytes
                    String conteudo = line.operands[0].substring(2, line.operands[0].length() - 1);
                    tamanho = conteudo.length();
                }
                else if (tipo == 'X') {
                    // X'F1' 1 byte
                    String conteudo = line.operands[0].substring(2, line.operands[0].length() - 1);
                    tamanho = conteudo.length() / 2;
                }
                else {
                    // BYTE 5  1 byte
                    tamanho = 1;
                }

                LOCCTR += tamanho;
                line.set_tamanho_instr(tamanho);
            }
            else
            {
                errorMsg = errorMsg + "\nERRO - Opcode Inválido: " + input.get(lineCounter);
            }

            intermediateFile.add(line);

            lineCounter +=1;
            line = new Line();
            line.parser(input.get(lineCounter));
        }

        intermediateFile.add(line);
    }

    // Gera código de máquina e arquivo temporário a partir da tabela de símbolos
    public void passoDois() {
        int lineCounter = 0;

        String obj = "";

        Line line = intermediateFile.get(lineCounter);

        if ( line.opcode.equals("START") )
        {
            lineCounter +=1;
        }

        line = intermediateFile.get(lineCounter);

        while ( !line.opcode.equals("END") )
        {
            if( OPTAB.getInstrucaoPorNome(line.opcode) != null )
            {

                // Nao possuimos instruçoes formato 1 (Alem de RD e WD que são tratadas a parte)

                if(line.tamanho_instr == 2)
                {
                    obj = montarF2(line);
                    output.machineCode.add(hexToBinary(obj));
                }

                else if(line.tamanho_instr > 2)
                {
                    int pc = line.address + line.tamanho_instr;
                    obj = montarF3F4(line,pc);
                    output.machineCode.add(hexToBinary(obj));
                }
            }
            else if (line.opcode.equals("RD"))
            {
                output.machineCode.add("11011000");
            }
            else if (line.opcode.equals("WD"))
            {
                output.machineCode.add("11011100");
            }
            else if(line.opcode.equals("BYTE"))
            {
                char tipo = line.operands[0].charAt(0);

                if (tipo == 'C') {
                    // BYTE C'EOF'
                    String conteudo = line.operands[0].substring(2, line.operands[0].length() - 1);
                    for (int i = 0; i < conteudo.length(); i++) {
                        obj = String.format("%02X", conteudo.charAt(i) & 0xFF);
                        output.machineCode.add(hexToBinary(obj));
                    }
                }
                else if (tipo == 'X') {
                    // BYTE X'F1'
                    String conteudo = line.operands[0].substring(2, line.operands[0].length() - 1);
                    output.machineCode.add(hexToBinary(conteudo));
                }
                else {
                    // BYTE 5
                    int valor = Integer.parseInt(line.operands[0]);
                    obj = String.format("%02X", valor & 0xFF);
                    output.machineCode.add(hexToBinary(obj));
                }

            }
            else if(line.opcode.equals("WORD"))
            {
                int word = Integer.parseInt(line.operands[0]);
                obj = String.format("%1$06X",word & 0xFFFFFF);
                output.machineCode.add(hexToBinary(obj));
            }
            else
            {
                errorMsg = errorMsg + "\nERRO - Opcode Inválido: " + input.get(lineCounter);
            }

            lineCounter+=1;
            line = intermediateFile.get(lineCounter);
        }

        output.endAddress = line.address;
        output.set_length();
    }

    private void gerarTXTOutput() {
        try (var fileWriter = new FileWriter(System.getProperty("user.dir") + "/ArquivosTXT/outputMontador.txt")) {
                fileWriter.write(String.join("\n", output.machineCode));
        }
        catch (IOException e) { errorMsg = errorMsg + "\nERRO - Erro ao gerar arquivo de saida."; }
    }

    private void mostrarMensagem() {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Arquivo de saida: ")
                .append(System.getProperty("user.dir"))
                .append("/ArquivosTXT/outputMontador.txt")
                .append("\n\n");
        if (errorMsg.isEmpty())
            mensagem.append("Programa montado com sucesso.");
        else
            mensagem.append("Programa montado com erros. Erro(s): \n")
                    .append(errorMsg);
        JOptionPane.showMessageDialog(null, mensagem, "Montador", JOptionPane.INFORMATION_MESSAGE);
    }

    //Monta instrucao do formato 2
    public String montarF2(Line line) {

        String opCode = String.format("%1$02X", OPTAB.getInstrucaoPorNome(line.opcode).getOpcode()& 0xFF);
        String operando1 = line.operands[0];
        String operando2 = line.operands[1];

        String r1;
        String r2;

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

    // Monta instrucao do formato 3 ou formato 4 (corrigido)
    // a ultima versão nao contava com operandos vazios, o que gerava disp negativa, tentando acessar pontos da memória inválidos
    public String montarF3F4(Line line, int PC) {
        int opcode = OPTAB.getInstrucaoPorNome(line.opcode).getOpcode() & 0xFF;

        // Determina ni a partir do prefixo
        int n = 0, i = 0;
        switch (line.prefix) {
            case ""  -> { n = 1; i = 1; } // addressing simples (n=1,i=1)
            case "#" -> { n = 0; i = 1; } // imediato (n=0,i=1)
            case "@" -> { n = 1; i = 0; } // indireto (n=1,i=0)
            default  -> {
                errorMsg = errorMsg + "\nERRO - Prefixo inválido: " + line.line;
                n = 1; i = 1;
            }
        }

        boolean isExtended = line.extended; // formato 4 se true
        boolean indexed = false;
        String operandField = (line.operands.length > 0) ? line.operands[0] : ""; // aqui resolve um dos erros da versao anterior

        // detectando indexado
        if (line.operands.length > 1 && "X".equals(line.operands[1])) {
            indexed = true;
        } else {
            // também aceita operandos no formato "LABEL,X"
            if (operandField != null && operandField.toUpperCase().endsWith(",X")) {
                indexed = true;
                operandField = operandField.substring(0, operandField.length() - 2).trim();
            }
        }

        // Tratar instruções sem operando (aqui q tava um dos erros da ultima versao)
        if (operandField == null) operandField = "";

        // calcula primeiro byte
        int firstByte = ((opcode & 0xFC) | ((n << 1) | i)) & 0xFF;

        // Bits x b p e numa nibble
        int x = indexed ? 1 : 0;
        int b = 0;
        int p = 0;
        int e = isExtended ? 1 : 0;

        // se nao tem operando disp/address = 0 (aqui!!!!!!)
        if (operandField.isEmpty()) {
            int xbpe = (x << 3) | (b << 2) | (p << 1) | e;
            if (isExtended) {
                int instr = (firstByte << 24) | (xbpe << 20) | (0 & 0xFFFFF);
                return String.format("%08X", instr & 0xFFFFFFFF);
            } else {
                int instr = (firstByte << 16) | (xbpe << 12) | (0 & 0xFFF);
                return String.format("%06X", instr & 0xFFFFFF);
            }
        }

        // lida com imediato numerico
        boolean immediateNumeric = false;
        int immediateValue = 0;
        if ("#".equals(line.prefix)) {
            try {
                immediateValue = Integer.parseInt(operandField);
                immediateNumeric = true;
            } catch (NumberFormatException ignored) {
                immediateNumeric = false; // pode ser símbolo
            }
        }

        // Formato 4
        if (isExtended) {
            int address = 0;
            if (immediateNumeric) {
                address = immediateValue & 0xFFFFF;
            } else {
                if (!SYMTAB.containsKey(operandField)) {
                    errorMsg = errorMsg + "\nERRO - Símbolo não definido (formato 4): " + operandField;
                    address = 0;
                } else {
                    address = SYMTAB.get(operandField) & 0xFFFFF;
                }
            }
            int xbpe = (x << 3) | (b << 2) | (p << 1) | e;
            int instr = (firstByte << 24) | (xbpe << 20) | (address & 0xFFFFF);
            return String.format("%08X", instr & 0xFFFFFFFF);
        }

        // Formato 3
        int disp = 0;

        if (immediateNumeric && n == 0 && i == 1) {
            // imediato com valor numérico -> usa valor direto no campo de 12 bits
            disp = immediateValue;
            // verifica se cabe (se deus fez é pq cabe (mas deus nao fez(fomos nós mesmo)))
            if (disp >= -2048 && disp <= 2047) {
                disp = disp & 0xFFF;
                p = 0; b = 0; // direct immediate
            } else {
                errorMsg = errorMsg + "\nERRO - Imediato numérico fora do alcance para formato 3: " + line.line;
                // imediato informado nao cabe no tipo de instrução. erro gerado
                disp = 0;
            }
        } else {
            if (!SYMTAB.containsKey(operandField)) {
                errorMsg = errorMsg + "\nERRO - Símbolo não definido: " + operandField;
                disp = 0;
            } else {
                int target = SYMTAB.get(operandField);
                // PC já vem com o endereco da prox instrucao
                int relative = target - PC;
                if (relative >= -2048 && relative <= 2047) {
                    // PC-relative
                    p = 1; b = 0;
                    disp = relative & 0xFFF;
                } else {
                    // tentar base-relative se existir registro BASE
                    if (SYMTAB.containsKey("BASE")) {
                        int baseAddr = SYMTAB.get("BASE");
                        int baseDisp = target - baseAddr;
                        if (baseDisp >= 0 && baseDisp <= 0xFFF) {
                            b = 1; p = 0;
                            disp = baseDisp & 0xFFF;
                        } else {
                            errorMsg = errorMsg + "\nERRO - Deslocamento fora do alcance (use + para formato 4): " + line.line;
                            disp = relative & 0xFFF;
                        }
                    } else {
                        errorMsg = errorMsg + "\nERRO - Deslocamento fora do alcance e BASE não definido (use + para formato 4): ";
                        disp = relative & 0xFFF;
                    }
                }
            }
        }

        // seta flags de endereçamento e deslocamento
        int xbpe = (x << 3) | (b << 2) | (p << 1) | e;
        int instr = ((firstByte & 0xFF) << 16) | ((xbpe & 0xF) << 12) | (disp & 0xFFF);
        return String.format("%06X", instr & 0xFFFFFF);
    }

    public Map<String, Integer> getSYMTAB() {
        return SYMTAB;
    }

    String hexToBinary(String hex) {
        String binary = "";

        hex = hex.toUpperCase();

        HashMap<Character, String> hashMap = new HashMap<>();

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

    public Output getOutput(){
        return this.output;
    }
}