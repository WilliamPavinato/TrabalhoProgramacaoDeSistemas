package ProcessadorDeMacros;

import java.util.*;
import java.io.*;
import Montador.Line;

public class ProcessadorDeMacros {
    private Map<String, Tupla> NAMTAB;          // nome da macro e ponteiros
    private Map<String, String> DEFTAB;         // nome da macro e códigos
    private Map<String, List<String>> ARGTAB;   // Tabela com os nomes dos macros e seus argumentos
    public Line line;

    private ArrayList<String> input = new ArrayList<String>();

    private ArrayList<String> output = new ArrayList<String>();
    private String errorMessage = "";

    public ProcessadorDeMacros() {
        NAMTAB = new HashMap<String, Tupla>();
        DEFTAB = new HashMap<String, String>();
        ARGTAB = new HashMap<String, List<String>>();
        line = new Line();

    }

    public void macroProcessor(String moduloIndex){

        int lineCounter = 0;
        boolean expanding = false;

        Line line = new Line();
        line.parser(input.get(lineCounter));

        while (!line.opcode.equals("END")){

            if (line.opcode.equals("MACRO")){
                //Inicializa variáveis de controle da macro
                StringBuffer macroCode = new StringBuffer();
                String macroName = line.label;
                Tupla tupla = new Tupla(lineCounter, 0);

                NAMTAB.put(macroName, tupla);
                ARGTAB.put(macroName, new ArrayList<>(line.macroArguments));

                lineCounter++;
                line.parser(input.get(lineCounter));

                // Escreve linha por linha da macro para salvar na DEFTAB
                while (!line.opcode.equals("MEND")){
                    macroCode.append(line.line + "\n");
                    lineCounter++;
                    line.parser(input.get(lineCounter));
                }
                // Define o ponteiro para o fim da macro e salva código e arguments
                NAMTAB.get(macroName).setEndPointer(lineCounter);
                DEFTAB.put(macroName, macroCode.toString());
                lineCounter += 2;
                line.parser(input.get(lineCounter));
            }
            // Se o opcode da linha está na DEFTAB, é uma chamada de macro
            else if (DEFTAB.containsKey(line.label)){       // Se a linha for uma chamada de macro, expande
                String macroBody = DEFTAB.get(line.label);
                List<String> macroArgs = ARGTAB.get(line.label);
                List<String> macroParams = line.macroArguments;

                for (int i = 0; i < macroArgs.size(); i++){
                    macroBody = macroBody.replaceAll(macroArgs.get(i), macroParams.get(i));
                }

                String[] linhas = macroBody.split("\\r?\\n");
                for (String linha : linhas){
                    Line tmpLine = new Line();
                    tmpLine = line;
                    tmpLine.parser(linha);
                    if (DEFTAB.containsKey(tmpLine.label)){
                        expandNestedMacros(linha);          // Se a linha expandida for um macro, expande o macro interno
                    }
                    else{
                        output.add(linha);
                    }
                }

                lineCounter++;
                line.parser(input.get(lineCounter));
            }
            else if (line.opcode.equals("START")){
                output.add(line.line);
                lineCounter++;
                line.parser(input.get(lineCounter));
                expanding = true;
            }
            else{                                         // Se a linha não for um macro, apenas copia
                output.add(line.line);
                lineCounter++;
                line.parser(input.get(lineCounter));
            }
        }

        output.add(line.line);
        gerarASMOutput(moduloIndex);
        return;
    }

    public void setPrograma(String codigoAssembly)
    {
        input.clear();
        String[] linhas = codigoAssembly.split("\\r?\\n");
        input.addAll(Arrays.asList(linhas));
    }

    public void expandNestedMacros(String linhaString) {
        Line tempLine = new Line();
        tempLine.parser(linhaString);

        // verifica se a linha expandida também é uma chamada de macro
        if (DEFTAB.containsKey(tempLine.opcode)) { // verifica o opcode, pois é onde fica o nome da chamada (ex: MACRO1 A,B)
            String macroBody = DEFTAB.get(tempLine.opcode);
            List<String> macroArgs = ARGTAB.get(tempLine.opcode);
            List<String> macroParams = tempLine.macroArguments;

            //substituição de parâmetros
            if (macroArgs != null && macroParams != null) {
                for (int i = 0; i < macroArgs.size() && i < macroParams.size(); i++) {
                    macroBody = macroBody.replaceAll(macroArgs.get(i), macroParams.get(i));
                }
            }

            // divide novamente e chama a função recursivamente
            String[] lines = macroBody.split("\\r?\\n");
            for (String subLine : lines) {
                expandNestedMacros(subLine);
            }
        } else {
            output.add(linhaString); //se não for macro, adiciona a linha processada ao output final
        }
    }

    public String getOutput() {
        return String.join("\n", output);
    }

    public void limpar() {
        NAMTAB.clear();
        DEFTAB.clear();
        ARGTAB.clear();
        input.clear();
        output.clear();
        errorMessage = "";
    }

    private void gerarASMOutput(String moduloIndex) {
        try (FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "/ArquivosTXT/outputMacroModulo" + moduloIndex + ".asm"))
        {
            fileWriter.write(String.join("\n", output));
            fileWriter.close();
        } catch (IOException e) {
            errorMessage = errorMessage + "\nERRO - Erro ao gerar arquivo de saida.";
        }
    }
}