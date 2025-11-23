package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STA extends Instruction {

    public STA() {
        super("STA", "0C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // pega o endereço de memória onde vamos salvar (apontado pelo PC)
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // avança o PC para a próxima instrução
        registradores.incrementarPC();

        // pega o valor que está no Acumulador "A"
        int accumulatorValue = registradores.getRegistradorPorNome("A").getValor();

        // converte o valor para Hexadecimal (String)
        String hexValue = Integer.toHexString(accumulatorValue).toUpperCase();

        // salva o valor na memória no endereço especificado
        memoria.setPosicaoMemoria(memoryAddress, hexValue);
    }
}