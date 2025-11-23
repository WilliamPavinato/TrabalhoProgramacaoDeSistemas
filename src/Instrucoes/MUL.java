package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class MUL extends Instruction {

    public MUL() {
        // Opcode "20" para MUL
        super("MUL", "20");
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
        // substituindo 'getValorIntSigned' por 'getValor', assumindo que a classe trata ints padrão
        int accumulatorValue = registradores.getRegistradorPorNome("A").getValor();

        // realiza a multiplicação (A * Memória)
        int result = accumulatorValue * memoryValue;

        // armazena o resultado de volta no Acumulador
        registradores.getRegistradorPorNome("A").setValor(result);
    }
}