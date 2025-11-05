package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;


    //Representa a instrução de Adição entre Registradores (ADDR).
    //Lógica: Reg_B <- Reg_A + Reg_B.

public class ADDR extends Instrucao {

    public ADDR() {
        super("ADDR", "90"); // Define nome e opcode para adição entre registradores
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {

        // Obtém o ID do primeiro registrador (reg A) a partir do PC
        String strIdRegA = memoria.getPosicaoMemoria(registradores.getValorPC());
        int idRegistradorA = Integer.parseInt(strIdRegA, 16); // Converte ID de hexa para inteiro
        registradores.incrementarPC(); // Avança o PC

        // O mesmo para o reg B
        String strIdRegB = memoria.getPosicaoMemoria(registradores.getValorPC());
        int idRegistradorB = Integer.parseInt(strIdRegB, 16); // Converte ID de hexa para inteiro
        registradores.incrementarPC(); // Avança o PC

        // Obtém os valores dos registradores.
        int valorRegistradorA = registradores.getRegistrador(idRegistradorA).getValor();
        int valorRegistradorB = registradores.getRegistrador(idRegistradorB).getValor();

        // Soma
        int resultadoSoma = valorRegistradorA + valorRegistradorB;

        // Armazena o resultado no reg B (Destino).
        registradores.getRegistrador(idRegistradorB).setValor(resultadoSoma); // Reg B <- Reg A + Reg B
    }
}