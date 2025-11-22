package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class JEQ extends Instruction {

    // Construtor: Define o nome e o opcode da instrução JEQ (Jump if Equal)
    public JEQ() {
        super("JEQ", "30"); // JEQ tem o opcode 30 em arquiteturas como SIC/XE
    }

    @Override
    public void executar(Memoria mem, Registradores reg) {
        // Verifica a condição: salta se o bit de Comparação (Equal flag) no 
        // Registrador Status Word (SW) for TRUE (geralmente representado por 0 após uma CMP)
        if (reg.getRegistradorPorNome("SW").getValor() == 0)
        {
            // Se a condição for verdadeira (os valores comparados são iguais):
            // 1. Obtém o endereço de memória que contém o endereço de salto (target address)
            int enderecoJump = Integer.parseInt(mem.getPosicaoMemoria(reg.getValorPC()),16);
            // 2. Desvia a execução: PC ← enderecoJump
            // Define o novo valor do Program Counter (PC) para o endereço de salto
            reg.getRegistradorPorNome("PC").setValor(enderecoJump);
        }
        else
        {
            reg.incrementarPC();
        }
    }
    
}