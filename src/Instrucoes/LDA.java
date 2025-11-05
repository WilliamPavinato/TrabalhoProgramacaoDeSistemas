package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDA extends Instruction {

    // Construtor: Define o nome e o opcode da instrução LDA (Load Accumulator)
    public LDA() {
        super("LDA", "00"); // LDA tem o opcode 00 (ou simplesmente 0) em arquiteturas como SIC/XE
    }


    @Override
    public void executar(Memoria mem, Registradores reg) {
        // 1. Obtém o endereço de memória do operando (o endereço está no PC)
        int enderecoMem = Integer.parseInt(mem.getPosicaoMemoria(reg.getValorPC()),16);
        
        // 2. Lê o valor (palavra) na posição de memória especificada
        // A ← (m..m+2)
        int valorMem = Integer.parseInt(mem.getPosicaoMemoria(enderecoMem),16);
        
        // 3. Atualiza o valor do registrador Acumulador (A) com a palavra lida
        reg.getRegistradorPorNome("A").setValor(valorMem);
        
        // 4. Incrementa o Program Counter (PC)
        reg.incrementarPC();
    }
}