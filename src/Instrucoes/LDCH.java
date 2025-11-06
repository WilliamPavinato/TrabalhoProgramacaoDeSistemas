package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDCH extends Instruction {

    // Construtor: Define o nome e o opcode da instrução LDCH
    public LDCH() {
        super("LDCH", "50");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Obtém o endereço de memória para o operando (o endereço está no PC)
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        // Lê o valor (palavra) na posição de memória especificada
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);
        // Isola o byte menos significativo (byte mais à direita) do valor lido
        int byteMenosSigMemoria = valorMem & 0xFF;

        // A[byte mais à direita] ← (m)
        // O valor lido (apenas o byte menos significativo) é o novo valor do registrador A
        int registradorA = byteMenosSigMemoria;
        // Atualiza o valor do registrador Acumulador (A)
        registradores.getRegistradorPorNome("A").setValor(registradorA);

        // Incrementa o Program Counter (PC) para apontar para a próxima instrução
        registradores.incrementarPC();
    }

}