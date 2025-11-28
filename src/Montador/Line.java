package Montador;

public class Line {

    // Variaveis publicas pra facilitar o acesso na classe Montador
    public String line = "";
    public String label = "";
    public String opcode = "";
    public String[] operands = new String[]{"", ""};
    public String prefix = "";
    public boolean extended = false;
    public boolean constant = false;
    public int tamanho_instr;

    public void parser(String inputLine) {
        // Reseta tudo antes de processar a linha nova
        this.resetarEstado();
        this.line = inputLine;

        if (inputLine == null || inputLine.trim().isEmpty()) return;

        // Uso regex \\s+ pra pegar qualquer espaço em branco (tab ou espaco normal)
        String[] tokens = inputLine.trim().split("\\s+");

        // Logica pra descobrir se a linha tem Label ou nao
        boolean temLabel = definirLayout(tokens);

        if (temLabel) {
            this.label = tokens[0];
            this.opcode = tokens[1];
            // Se tiver mais coisas depois do opcode, sao os operandos
            if (tokens.length > 2) extrairOperandos(tokens[2]);
        } else {
            this.label = "";
            this.opcode = tokens[0];
            if (tokens.length > 1) extrairOperandos(tokens[1]);
        }

        detectarExtendido();
    }

    // Zera as variaveis da instancia
    private void resetarEstado() {
        this.label = "";
        this.opcode = "";
        this.operands[0] = "";
        this.operands[1] = "";
        this.prefix = "";
        this.extended = false;
        this.constant = false;
    }

    // Regras pra saber onde esta o Label
    private boolean definirLayout(String[] tokens) {
        String first = tokens[0];

        // Instrucoes especificas que nunca tem label na frente nessa logica
        if (first.equals("RD") || first.equals("WD") || first.equals("END")) {
            return false;
        }

        // Se a segunda palavra for RD ou WD, entao a primeira eh label
        if (tokens.length > 1 && (tokens[1].equals("RD") || tokens[1].equals("WD"))) {
            return true;
        }

        // Se tiver 3 ou mais partes, assume que tem label
        return tokens.length >= 3;
    }

    // Trata os operandos e remove os caracteres especiais (#, @)
    private void extrairOperandos(String rawOperands) {
        String[] parts = rawOperands.split(",");
        String op1 = parts[0];

        // Verifica endereçamento imediato ou indireto
        if (op1.startsWith("#")) {
            this.prefix = "#";
            this.operands[0] = op1.substring(1);
        } else if (op1.startsWith("@")) {
            this.prefix = "@";
            this.operands[0] = op1.substring(1);
        } else {
            this.prefix = "";
            this.operands[0] = op1;
        }

        // Pega o segundo operando se existir
        if (parts.length > 1) {
            this.operands[1] = parts[1];
        } else {
            this.operands[1] = "";
        }
    }

    // Verifica formato 4 (+)
    private void detectarExtendido() {
        if (this.opcode.startsWith("+")) {
            this.extended = true;
            this.opcode = this.opcode.substring(1); // Tira o + do opcode
        }
    }

    // Setter pro tamanho
    public void set_tamanho_instr(int LOCCTR){
        this.tamanho_instr = LOCCTR;
    }
}