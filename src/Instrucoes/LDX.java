package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDX extends Instruction {

    public LDX() {
        super("LDX", "04");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // pega o endereço de memória (operando) apontado pelo PC
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // avança o PC para a próxima instrução
        registradores.incrementarPC();

        // busca o valor armazenado naquele endereço de memória
        int memoryValue = Integer.parseInt(memoria.getPosicaoMemoria(memoryAddress), 16);

        // salva o valor no Registrador X
        registradores.getRegistradorPorNome("X").setValor(memoryValue);
    }
}