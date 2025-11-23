package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class OR extends Instruction {

    public OR() {
        super("OR", "44");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // pega o endereço de memória apontado pelo PC
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // avança o PC para a próxima instrução
        registradores.incrementarPC();

        // busca o valor armazenado naquele endereço de memória
        int memoryValue = Integer.parseInt(memoria.getPosicaoMemoria(memoryAddress), 16);

        // busca o valor atual do Acumulador (A)
        int accumulatorValue = registradores.getRegistradorPorNome("A").getValor();

        // realiza a operação OR bit-a-bit (|)
        int result = accumulatorValue | memoryValue; // A | m

        // armazena o resultado de volta no Acumulador
        registradores.getRegistradorPorNome("A").setValor(result);
    }
}