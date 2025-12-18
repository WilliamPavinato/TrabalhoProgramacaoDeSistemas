package Montador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Line {

    // Variaveis publicas pra facilitar o acesso na classe Montador
    public String line       = "";
    public String label      = "";
    public String opcode     = "";
    public String[] operands = new String[2];
    public String prefix     = "";
    public boolean extended  = false;
    public boolean constant  = false;
    public int tamanho_instr;
    public int address;
    public List<String> macroArguments = new ArrayList<>();

    public void parser(String Line){
        this.line = Line;
        String[] loo = Line.split(" ");

        if (loo[0].equals("MACRO")){
            opcode = loo[0];
            label = loo[1];
            String[] aux = loo[2].split(",");
            macroArguments.addAll(Arrays.asList(aux));
            return;
        }
        else if (loo[0].equals("MEND")){
            label = "";
            opcode = loo[0];
            return;
        }
        else if (loo[0].equals("RD") || loo[0].equals("WD") || loo[0].equals("END")) {
            label = "";
            opcode = loo[0];
            return;
        }
        else if (loo[1].equals("RD") || loo[1].equals("WD")) {
            label = loo[0];
            opcode = loo[1];
            return;
        }

        if(loo.length <3) { // Sem label
            label = "";
            opcode = loo[0];
            String[] aux = loo[1].split(",");
            if(aux.length > 1){
                operands[1] = aux[1];
            }
            else{
                operands[1] = "";
            }
            operands[0] = aux[0];
        }
        else { // Com label
            label = loo[0];
            opcode = loo[1];
            String[] aux = loo[2].split(",");
            if(aux.length >1){
                operands[1] = aux[1];
            }
            else{
                operands[1] = "";
            }
            operands[0] = aux[0];
        }

        // Remover prefixos dos operandos
        if(operands[0].contains("#")) {
            prefix = "#";
            StringBuilder sb = new StringBuilder(operands[0]);
            sb.deleteCharAt(0);
            operands[0] = sb.toString();
        }
        else if(operands[0].contains("@")) {
            prefix = "@";
            StringBuilder sb = new StringBuilder(operands[0]);
            sb.deleteCharAt(0);
            operands[0] = sb.toString();
        }
        else { prefix = ""; }

        // Remover prefixos da instrução
        if(opcode.contains("+")) {
            extended = true;
            StringBuilder sb = new StringBuilder(opcode);
            sb.deleteCharAt(0);
            opcode = sb.toString();
        }
    }

    // Setter pro tamanho
    public void set_tamanho_instr(int LOCCTR){
        this.tamanho_instr = LOCCTR;
    }

    // Setter pro endereco
    public void setAddress(int address) {
        this.address = address;
    }
}