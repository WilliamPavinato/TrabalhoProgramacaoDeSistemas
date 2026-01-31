package Montador;

import java.util.ArrayList;
import java.util.List;

public class Line {

    public String line = "";
    public String label = "";
    public String opcode = "";
    public List<String> operands = new ArrayList<>();
    public List<String> macroArguments = new ArrayList<>();
    public List<String> macroNames = new ArrayList<>();
    public String prefix = "";
    public boolean extended = false;
    public boolean constant = false;
    public int tamanho_instr;

    public void parser(String Line){
        this.line = Line;
        String[] loo = Line.split(" ");

        if (loo[0].charAt(0) == '&'){              // Chamada de macro dentro de macro
            StringBuilder sb = new StringBuilder(line);
            sb.deleteCharAt(0);
            line = sb.toString();
            if (loo.length > 1){                        // Se tiver argumentos (não obrigatório)
                String[] aux = loo[1].split(",");
                for (String arg : aux){
                    macroArguments.add(arg);
                }
            }
            return;
        }

        else if (loo[0].equals("MACRO")){            // DEFININDO
            macroArguments.clear();
            opcode = loo[0];
            label = loo[1];
            macroNames.add(label);
            if (loo.length > 2){                            // Se tiver argumentos (não obrigatório)
                String[] aux = loo[2].split(",");
                for (String arg : aux){
                    macroArguments.add(arg);
                }
            }
            return;
        }

        else if (loo[0].equals("MEND")){
            label = "";
            opcode = loo[0];
            return;
        }

        else if (macroNames.contains(loo[0])){          // EXPANDINDO
            label = loo[0];
            macroArguments.clear();
            if (loo.length > 1){                        // Se tiver argumentos (não obrigatório)
                String[] aux = loo[1].split(",");
                for (String arg : aux){
                    macroArguments.add(arg);
                }
            }
            return;
        }

        else if (loo[0].equals("RD") || loo[0].equals("WD") || loo[0].equals("END"))
        {
            label = "";
            opcode = loo[0];
            return;
        }
        else if (loo[1].equals("RD") || loo[1].equals("WD"))
        {
            label = loo[0];
            opcode = loo[1];
            return;
        }

        if(loo.length <3) // Sem label
        {
            label = "";
            opcode = loo[0];
            String[] aux = loo[1].split(",");
            for (int i = 0; i < aux.length; i++){
                operands.add(aux[i]);
            }
        }
        else // Com label
        {
            label = loo[0];
            opcode = loo[1];
            String[] aux = loo[2].split(",");
            for (int i = 0; i < aux.length; i++){
                operands.add(aux[i]);
            }
        }

        // Remover prefixos dos operandos
        for (int i = 0; i < operands.size(); i++){
            if(operands.get(i).contains("#"))
            {
                prefix = "#";
                StringBuilder sb = new StringBuilder(operands.get(i));
                sb.deleteCharAt(0);
                operands.set(i, sb.toString());
            }
            else if(operands.get(i).contains("@"))
            {
                prefix = "@";
                StringBuilder sb = new StringBuilder(operands.get(i));
                sb.deleteCharAt(0);
                operands.set(i, sb.toString());
            }
            else
            {
                prefix = "";
            }
        }

        // Remover prefixos da instrução
        if(opcode.contains("+")){
            extended = true;
            StringBuilder sb = new StringBuilder(opcode);
            sb.deleteCharAt(0);
            opcode = sb.toString();
        }

    }

    public void set_tamanho_instr(int LOCCTR){
        tamanho_instr = LOCCTR;
    }
}