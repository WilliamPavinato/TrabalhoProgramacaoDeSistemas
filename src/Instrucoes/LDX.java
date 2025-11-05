package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDX extends Instruction {

    public LDX() {
        super("LDX", "04"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o endereço de memória (parâmetro 1) do PC
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Lê e avança PC (seguindo a ordem do ADD)
        registradores.incrementarPC();

        // Pega o valor armazenado na posição de memória lida
        int memoryValue = Integer.parseInt(memoria.getPosicaoMemoria(memoryAddress), 16);

        // A instrução LDX (Load X) armazena o valor da memória no registrador "X"
        registradores.getRegistradorPorNome("X").setValor(memoryValue); // Armazena resultado no "X"
    }
}