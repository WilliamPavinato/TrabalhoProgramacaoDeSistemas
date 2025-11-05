package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class JGT extends Instruction {

    // Construtor: Define o nome e o opcode da instrução JGT (Jump if Greater Than)
    public JGT() {
        super("JGT", "34"); // JGT tem o opcode 34 em arquiteturas como SIC/XE
    }

    @Override
    public void executar(Memoria mem, Registradores reg) {
        // Verifica a condição: salta se o bit de Comparação (Greater  Than flag) no 
        // Registrador Status Word (SW ) for TRUE (geralmente representado por 1 após uma CMP)
        if (reg.getRegistradorPorNome("SW").getValor() == 1)
        {
            // Se a condição for verdadeira (o primeiro valor comparado é maior que o segundo):
            // 1. Obtém o endereço de memória que contém o endereço de salto 
            int enderecoJump = Integer.parseInt(mem.getPosicaoMemoria(reg.getValorPC()),16);
            // 2. Desvia a execução: PC ← enderecoJump
            // Define o novo valor do Program Counter (PC) para o endereço de salto
            reg.getRegistradorPorNome("PC").setValor(enderecoJump);
        }
        
        // O PC é sempre incrementado para avançar sobre o endereço do operando
        // (o endereço de salto), seja o salto executado ou não.
        reg.incrementarPC();
    }
}