package Instrucoes;

import java.util.HashMap;
import java.util.Map;

// Esta classe é o catálogo central de todas as instruções válidas no simulador
public class Instructions {
    
    // Mapa que armazena pares de Opcode (Byte) e a Instância da Instrução (Instruction).
    private final Map<Byte, Instruction> instrucoes;

    // Construtor: Inicializa o mapa e carrega todas as instruções.
    public Instructions() {
        instrucoes = new HashMap<>();

        // Lógicas e Aritméticas
        instrucoes.put((byte)0x18, new ADD());    // Adiciona valor da memória ao Acumulador (A)
        instrucoes.put((byte)0x90, new ADDR());   // Adiciona Reg A ao Reg B (Reg B <- Reg A + Reg B)
        instrucoes.put((byte)0x40, new AND());    // AND lógico entre memória e A (A <- A & Mem)
        instrucoes.put((byte)0x4,  new CLEAR());  // Zera o valor de um registrador
        instrucoes.put((byte)0x28, new COMP());   // Compara memória com A (seta SW)
        instrucoes.put((byte)0xA0, new COMPR());  // Compara Reg A com Reg B (seta SW)
        instrucoes.put((byte)0x24, new DIV());    // Divide A pelo valor da memória (A <- A / Mem)
        instrucoes.put((byte)0x9C, new DIVR());   // Divide Reg A por Reg B (Reg B <- Reg A / Reg B)
        instrucoes.put((byte)0x20, new MUL());    // Multiplica A pelo valor da memória (A <- A * Mem)
        instrucoes.put((byte)0x98, new MULR());   // Multiplica Reg A por Reg B (Reg B <- Reg A * Reg B)
        instrucoes.put((byte)0x44, new OR());     // OR lógico entre memória e A (A <- A | Mem)
        instrucoes.put((byte)0x1C, new SUB());    // Subtrai valor da memória de A (A <- A - Mem)
        instrucoes.put((byte)0x94, new SUBR());   // Subtrai Reg B de Reg A (Reg B <- Reg A - Reg B)

        // Salto e Control
        instrucoes.put((byte)0x3C, new J());      // Salto incondicional para endereço
        instrucoes.put((byte)0x30,  new JEQ());    // Salto se SW for igual (J = EQual)
        instrucoes.put((byte)0x34, new JGT());    // Salto se SW for maior (J = Greater Than)
        instrucoes.put((byte)0x38, new JLT());    // Salto se SW for menor (J = Less Than)
        instrucoes.put((byte)0x48, new JSUB());   // Salto para Sub-rotina (armazena retorno em L)
        instrucoes.put((byte)0x4C, new RSUB());   // Retorno de Sub-rotina (PC <- L)

        // L and S
        instrucoes.put((byte)0x0,  new LDA());    // Carrega Acumulador (A)
        instrucoes.put((byte)0x68, new LDB());    // Carrega Registrador Base (B)
        instrucoes.put((byte)0x50, new LDCH());   // Carrega Caractere (A <- 1º byte da Memória)
        instrucoes.put((byte)0x8,  new LDL());    // Carrega Registrador Linkage (L)
        instrucoes.put((byte)0x6C, new LDS());    // Carrega Registrador Geral (S)
        instrucoes.put((byte)0x74, new LDT());    // Carrega Registrador Geral (T)
        instrucoes.put((byte)0x04, new LDX());    // Carrega Registrador Index (X)

        instrucoes.put((byte)0x0C, new STA());    // Armazena Acumulador (A)
        instrucoes.put((byte)0x78, new STB());    // Armazena Registrador Base (B)
        instrucoes.put((byte)0x54, new STCH());   // Armazena Caractere (1º byte de A na Memória)
        instrucoes.put((byte)0x14, new STL());    // Armazena Registrador Linkage (L)
        instrucoes.put((byte)0x7C, new STS());    // Armazena Registrador Geral (S)
        instrucoes.put((byte)0x84, new STT());    // Armazena Registrador Geral (T)
        instrucoes.put((byte)0x10, new STX());    // Armazena Registrador Index (X)

        // --- Outras Instruções ---
        instrucoes.put((byte)0xAC, new RMO());    // Move valor entre registradores
        instrucoes.put((byte)0xA4, new SHIFTL()); // Shift Lógico para Esquerda
        instrucoes.put((byte)0xA8, new SHIFTR()); // Shift Lógico para Direita
        instrucoes.put((byte)0x2C, new TIX());    // Testa e Incrementa X (X <- X + 1, seta SW)
        instrucoes.put((byte)0xB8, new TIXR());   // Testa e Incrementa X com Registrador (X <- X + Reg, seta SW)
    }

    public Instruction getInstrucao(byte opcode) {
        return instrucoes.get(opcode);
    }

    public Instruction getInstrucaoPorNome(String nome)
    {
        for (Instruction instrucao : instrucoes.values()) {
            if (instrucao.getNome().equals(nome))
                return instrucao;
        }
        return null;
    }
}