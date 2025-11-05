package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDT extends Instruction {

    public LDT() {
        super("LDT", "74"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o endereço de memória (parâmetro 1) do PC
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Lê e avança PC
        registradores.incrementarPC();

        // Pega o valor armazenado na posição de memória lida
        int memoryValue = Integer.parseInt(memoria.getPosicaoMemoria(memoryAddress), 16);

        // A instrução LDT (Load T) armazena o valor da memória no registrador "T"
        registradores.getRegistradorPorNome("T").setValor(memoryValue); // Armazena resultado no "T"
    }
}