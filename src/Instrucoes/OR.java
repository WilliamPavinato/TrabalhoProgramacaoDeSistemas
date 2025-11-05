package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class OR extends Instruction {

    public OR() {
        super("OR", "44"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o endereço de memória (parâmetro 1) do PC
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Lê e avança PC
        registradores.incrementarPC();

        // Pega o valor armazenado na posição de memória lida
        int memoryValue = Integer.parseInt(memoria.getPosicaoMemoria(memoryAddress), 16);

        // Pega o valor que está no acumulador "A"
        int accumulatorValue = registradores.getRegistradorPorNome("A").getValor();

        // Acumulador = Acumulador | valorMem (Operação 'OU' bit-a-bit)
        accumulatorValue = accumulatorValue | memoryValue; // Operação OR

        // Acumulador "A" recebe o resultado
        registradores.getRegistradorPorNome("A").setValor(accumulatorValue); // Armazena resultado
    }
}