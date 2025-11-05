package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

// Zera registrador

public class CLEAR extends Instruction {

    public CLEAR() {
        super("CLEAR", "4"); // Define nome e opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Leitura do ID do Registrador 
        // Pega o ID do registrador (param 1) a partir do PC
        String strIdRegistrador = memoria.getPosicaoMemoria(registradores.getValorPC());
        int idRegistrador = Integer.parseInt(strIdRegistrador, 16); // Converte ID de hexa para int

        // Avança o PC
        registradores.incrementarPC();

        // Zera o valor 
        registradores.getRegistrador(idRegistrador).setValor(0);
    }
}