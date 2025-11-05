package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDS extends Instruction {
    
    // Construtor: Define o nome e o opcode da instrução LDS
    public LDS() {
        super("LDS", "6C"); // LDS tem o opcode 6C em arquiteturas como SIC/XE
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Obtém o endereço de memória para o operando (o endereço está no PC)
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        // Lê o valor (palavra) na posição de memória especificada
        // S ← (m..m+2)
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);

        // Atualiza o valor do registrador Index S com a palavra lida da memória
        registradores.getRegistradorPorNome("S").setValor(valorMem);

        // Incrementa o Program Counter (PC) para apontar para a próxima instrução
        registradores.incrementarPC();
    }
}