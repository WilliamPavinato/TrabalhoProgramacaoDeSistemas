package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class RMO extends Instruction {

    public RMO() {
        super("RMO", "AC"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o ID do primeiro registrador (RegA - origem) do PC
        int registerA_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Avança PC
        registradores.incrementarPC();

        // Pega o ID do segundo registrador (RegB - destino) do PC
        int registerB_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Avança PC
        registradores.incrementarPC();

        // Pega o valor armazenado no primeiro registrador (RegA)
        int registerA_Value = registradores.getRegistrador(registerA_ID).getValor();

        // RegB = RegA (Move o valor de RegA para RegB)
        // O segundo registrador (RegB) recebe o valor
        registradores.getRegistrador(registerB_ID).setValor(registerA_Value); // Armazena resultado
    }
}