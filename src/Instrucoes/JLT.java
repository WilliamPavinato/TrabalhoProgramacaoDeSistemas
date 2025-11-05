package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class JLT extends Instruction {

    // Construtor: Define o nome e o opcode da instrução JLT (Jump if Less Than)
    public JLT() {
        super("JLT", "38"); // JLT tem o opcode 38 em arquiteturas como SIC/XE
    }


    @Override
    public void executar(Memoria mem, Registradores reg) {
        // Verifica a condição: salta se o bit de Comparação (Less Than flag) no 
        // Registrador Status Word (SW) for TRUE (geralmente representado por -1 após uma CMP)
        if (reg.getRegistradorPorNome("SW").getValor() == -1)
        {
            // Se a condição for verdadeira (o primeiro valor comparado é menor que o segundo):
            // 1. Obtém o endereço de memória que contém o endereço de salto (target address)
            int enderecoJump = Integer.parseInt(mem.getPosicaoMemoria(reg.getValorPC()),16);
            // 2. Desvia a execução: PC ← enderecoJump
            // Define o novo valor do Program Counter (PC) para o endereço de salto
            reg.getRegistradorPorNome("PC").setValor(enderecoJump);
        }
        
        // O PC é sempre incrementado
        reg.incrementarPC();
    }
}