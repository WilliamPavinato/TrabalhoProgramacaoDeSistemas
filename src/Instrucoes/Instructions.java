package Instrucoes;

import java.util.HashMap;
import java.util.Map;

// Esta classe é o catálogo central de todas as instruções válidas no simulador,
// mapeando o opcode hexadecimal (chave) para a instância da classe Instruction (valor).
public class Instructions { // CLASSE RENOMEADA PARA Instructions
    
    // Mapa que armazena pares de Opcode (String) e a Instância da Instrução (Instruction).
    // CORREÇÃO: Usando a classe base 'Instruction' para consistência.
    private final Map<String, Instruction> instrucoes;

    // Construtor: Inicializa o mapa e carrega todas as instruções.
    public Instructions() {
        instrucoes = new HashMap<>();

        // Lógicas e Aritméticas
        instrucoes.put("18", new ADD());    // Adiciona valor da memória ao Acumulador (A)
        instrucoes.put("90", new ADDR());   // Adiciona Reg A ao Reg B (Reg B <- Reg A + Reg B)
        instrucoes.put("40", new AND());    // AND lógico entre memória e A (A <- A & Mem)
        instrucoes.put("4", new CLEAR());   // Zera o valor de um registrador
        instrucoes.put("28", new COMP());   // Compara memória com A (seta SW)
        instrucoes.put("A0", new COMPR());  // Compara Reg A com Reg B (seta SW)
        instrucoes.put("24", new DIV());    // Divide A pelo valor da memória (A <- A / Mem)
        instrucoes.put("9C", new DIVR());   // Divide Reg A por Reg B (Reg B <- Reg A / Reg B)
        instrucoes.put("20", new MUL());    // Multiplica A pelo valor da memória (A <- A * Mem)
        instrucoes.put("98", new MULR());   // Multiplica Reg A por Reg B (Reg B <- Reg A * Reg B)
        instrucoes.put("44", new OR());     // OR lógico entre memória e A (A <- A | Mem)
        instrucoes.put("1C", new SUB());    // Subtrai valor da memória de A (A <- A - Mem)
        instrucoes.put("94", new SUBR());   // Subtrai Reg B de Reg A (Reg B <- Reg A - Reg B)

        // Salto e Control
        instrucoes.put("3C", new J());      // Salto incondicional para endereço
        instrucoes.put("30", new JEQ());    // Salto se SW for igual (J = EQual)
        instrucoes.put("34", new JGT());    // Salto se SW for maior (J = Greater Than)
        instrucoes.put("38", new JLT());    // Salto se SW for menor (J = Less Than)
        instrucoes.put("48", new JSUB());   // Salto para Sub-rotina (armazena retorno em L)
        instrucoes.put("4C", new RSUB());   // Retorno de Sub-rotina (PC <- L)

        // L and S
        instrucoes.put("0", new LDA());     // Carrega Acumulador (A)
        instrucoes.put("68", new LDB());    // Carrega Registrador Base (B)
        instrucoes.put("50", new LDCH());   // Carrega Caractere (A <- 1º byte da Memória)
        instrucoes.put("8", new LDL());     // Carrega Registrador Linkage (L)
        instrucoes.put("6C", new LDS());    // Carrega Registrador Geral (S)
        instrucoes.put("74", new LDT());    // Carrega Registrador Geral (T)
        instrucoes.put("04", new LDX());    // Carrega Registrador Index (X)
        
        instrucoes.put("0C", new STA());    // Armazena Acumulador (A)
        instrucoes.put("78", new STB());    // Armazena Registrador Base (B)
        instrucoes.put("54", new STCH());   // Armazena Caractere (1º byte de A na Memória)
        instrucoes.put("14", new STL());    // Armazena Registrador Linkage (L)
        instrucoes.put("7C", new STS());    // Armazena Registrador Geral (S)
        instrucoes.put("84", new STT());    // Armazena Registrador Geral (T)
        instrucoes.put("10", new STX());    // Armazena Registrador Index (X)

        // --- Outras Instruções ---
        instrucoes.put("AC", new RMO());    // Move valor entre registradores
        instrucoes.put("A4", new SHIFTL()); // Shift Lógico para Esquerda
        instrucoes.put("A8", new SHIFTR()); // Shift Lógico para Direita
        instrucoes.put("2C", new TIX());    // Testa e Incrementa X (X <- X + 1, seta SW)
        instrucoes.put("B8", new TIXR());   // Testa e Incrementa X com Registrador (X <- X + Reg, seta SW)
    }

    // Método para buscar e retornar a instância da instrução com base no Opcode.
    public Instruction getInstrucao(String opcode) {
        return instrucoes.get(opcode);
    }
}