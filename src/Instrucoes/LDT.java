package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDT extends Instruction {

    public LDT() {
        super("LDT", "74");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // pega o endereço de memória (que é o operando escrito pelo montador logo após o opcode)
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // avança o PC para pular esse endereço e apontar para a próxima instrução
        registradores.incrementarPC();

        // vai até o endereço de memória lido e pega o valor armazenado lá
        int memoryValue = Integer.parseInt(memoria.getPosicaoMemoria(memoryAddress), 16);

        // salva esse valor no Registrador T
        registradores.getRegistradorPorNome("T").setValor(memoryValue);
    }
}