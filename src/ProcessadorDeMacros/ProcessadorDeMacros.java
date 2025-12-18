package ProcessadorDeMacros;

import java.util.*;

import Montador.Line;

public class ProcessadorDeMacros {
    private Map<String, Tupla> NAMTAB;          // nome da macro e ponteiros
    private Map<String, String> DEFTAB;         // nome da macro e códigos
    private Map<String, List<String>> ARGTAB;   // Tabela com os nomes dos macros e seus argumentos

    private ArrayList<String> input = new ArrayList<String>();

    private ArrayList<String> output = new ArrayList<String>();

    public ProcessadorDeMacros() {
        NAMTAB = new HashMap<String, Tupla>();
        DEFTAB = new HashMap<String, String>();
        ARGTAB = new HashMap<String, List<String>>();
    }

    public void macroProcessor(){

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
                ARGTAB.put(macroName, new ArrayList<String>());

                lineCounter++;
                line.parser(input.get(lineCounter));

                // Escreve linha por linha da macro para salvar na DEFTAB
                while (!line.label.equals("MEND")){
                    macroCode.append(line.line + "\n");
                    lineCounter++;
                    line.parser(input.get(lineCounter));
                }
                // Define o ponteiro para o fim da macro e salva código e arguments
                NAMTAB.get(macroName).setEndPointer(lineCounter);
                DEFTAB.put(macroName, macroCode.toString());
                ARGTAB.put(macroName, line.macroArguments);
            }
            // Se o opcode da linha está na DEFTAB, é uma chamada de macro
            else if (DEFTAB.containsKey(line.opcode)) {
                String macroBody = DEFTAB.get(line.opcode);
                List<String> macroArgs = ARGTAB.get(line.opcode); // argumentos formais (&ARG1, etc)
                List<String> macroParams = line.macroArguments;   // argumentos reais (A, B, etc)

                // substitui os argumentos formais pelos reais no corpo da macro
                if (macroArgs != null && macroParams != null) {
                    for (int i = 0; i < macroArgs.size() && i < macroParams.size(); i++) {
                        macroBody = macroBody.replaceAll(macroArgs.get(i), macroParams.get(i)); // substitui todas as ocorrências de &ARG pelo valor passado
                    }
                }

                // civide o corpo expandido em linhas e processa recursivamente
                String[] linhasExpandidas = macroBody.split("\\r?\\n");
                for (String linhaExp : linhasExpandidas) {
                    expandNestedMacros(linhaExp);
                }

                // avança para a próxima linha do input original
                lineCounter++;
                if (lineCounter < input.size()) {
                    line.parser(input.get(lineCounter));
                }
            }
        }
    }

    public void setPrograma(String codigoAssembly)
    {
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
}
