package ProcessadorDeMacros;

import java.util.*;

import Montador.Line;

public class ProcessadorDeMacros {
    private Map<String, Tupla> NAMTAB;          // nome da macro e ponteiros
    private Map<String, String> DEFTAB;         // nome da macro e códigos
    private Map<String, List<String>> ARGTAB;   // Tabela com os nomes dos macros e seus argumentos

    private ArrayList<String> input = new ArrayList<String>();


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
        }
    }

    public void setPrograma(String codigoAssembly)
    {
        String[] linhas = codigoAssembly.split("\\r?\\n");
        input.addAll(Arrays.asList(linhas));
    }
}
