package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDL extends Instruction {

    // Construtor: Define o nome e o opcode da instrução LDL
    public LDL() {
        super("LDL", "08"); // LDL tem o opcode 08 
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Obtém o endereço de memória para o operando (o endereço está no PC)
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        // Lê o valor (palavra, geralmente 3 bytes) na posição de memória especificada
        // L ← (m..m+2)
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);

        // O valor lido (a palavra completa) é o novo valor do registrador L
        int registradorL = valorMem;
        // Atualiza o valor do registrador Linkage (L)
        registradores.getRegistradorPorNome("L").setValor(registradorL);

        // Incrementa o Program Counter (PC) para apontar para a próxima instrução
        registradores.incrementarPC();
    }

}