package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SUB extends Instruction {

    public SUB() {
        super("SUB", "1C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // pega o endereço de memória (operando) apontado pelo PC
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança o PC para a próxima instrução
        registradores.incrementarPC();

        //busca o valor armazenado naquele endereço de memória
        int memoryValue = Integer.parseInt(memoria.getPosicaoMemoria(memoryAddress), 16);

        //pega o valor que está no acumulador "A"
        int accumulatorValue = registradores.getRegistradorPorNome("A").getValor();

        // raliza a subtração: Acumulador = Acumulador - ValorMemória
        int result = accumulatorValue - memoryValue;

        // armazena o resultado de volta no Acumulador
        registradores.getRegistradorPorNome("A").setValor(result);
    }
}