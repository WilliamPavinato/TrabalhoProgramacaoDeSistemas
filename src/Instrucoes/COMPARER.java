package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;


// Compara o valor do Registrador A com o valor do Registrador B e define o registrador

public class COMPARER extends Instruction {

    public COMPARER() {
        super("COMPARER", "A0"); // Define nome e opcode para comparação entre registradores
    }


    @Override
    public void executar(Memoria memoria, Registradores registradores) {

        // Obtém o ID do primeiro registrador reg A a partir do PC
        String strIdRegA = memoria.getPosicaoMemoria(registradores.getValorPC());
        int idRegistradorA = Integer.parseInt(strIdRegA, 16); // Converte ID de hexadecimal para inteiro
        registradores.incrementarPC(); // Avança o PC

        
        // Obtém o ID do primeiro registrador reg B a partir do PC
        String strIdRegB = memoria.getPosicaoMemoria(registradores.getValorPC());
        int idRegistradorB = Integer.parseInt(strIdRegB, 16); // Converte ID de hexadecimal para inteiro
        registradores.incrementarPC(); // Avança o PC

        // Obtém os valores dos registradores a serem comparados
        int valorRegistradorA = registradores.getRegistrador(idRegistradorA).getValor();
        int valorRegistradorB = registradores.getRegistrador(idRegistradorB).getValor();

        int resultadoComparacao;

        // Compara reg A com reg B e define o valor para o Status Word 
        if (valorRegistradorA == valorRegistradorB) {
            // Se reg A IGUAL a reg B, define SW como 0.
            resultadoComparacao = 0;
        } else if (valorRegistradorA < valorRegistradorB) {
            // Se reg A MENOR que reg B, define SW como -1.
            resultadoComparacao = -1;
        } else {
            // Se reg A MAIOR que reg B, define SW como 1.
            resultadoComparacao = 1;
        }
        
        // Armazena o resultado da comparação no registrador Status Word (SW).
        registradores.getRegistradorPorNome("SW").setValor(resultadoComparacao);
    }
}