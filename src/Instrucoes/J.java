package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class J extends Instruction {

    // Construtor: Define o nome e o opcode da instrução J (Jump Incondicional)
    public J() {
        super("J", "3C"); // J tem o opcode 3C em arquiteturas como SIC/XE
    }

    @Override
    public void executar(Memoria mem, Registradores reg) {
        // Obtém o endereço de memória que contém o endereço de salto (target address)
        // O endereço do operando está no PC
        int enderecoJump = Integer.parseInt(mem.getPosicaoMemoria(reg.getValorPC()),16);

        // Desvia a execução: PC ← enderecoJump
        // Define o novo valor do Program Counter (PC) para o endereço de salto
        reg.getRegistradorPorNome("PC").setValor(enderecoJump);

    }

}