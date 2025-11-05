package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

    //Compara o valor do Acumulador

public class COMPARE extends Instrucao {

    public COMPARE() {
        super("COMPARE", "28"); // Define nome e opcode para a operação 
    }


    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o endereço de memória (param 1) apontado pelo PC
        String strEndereco = memoria.getPosicaoMemoria(registradores.getValorPC());
        int enderecoMem = Integer.parseInt(strEndereco, 16); // Converte de hex para int

        // Avança o PC
        registradores.incrementarPC();

        // Pega o valor armazenado no endereço de memória lido
        String strValorMemoria = memoria.getPosicaoMemoria(enderecoMem);
        int valorMem = Integer.parseInt(strValorMemoria, 16); // Converte de hex para int


        // Obtém o valor do acumulador
        int valorAcumulador = registradores.getRegistradorPorNome("A").getValor();

        // Referência ao registrador de status (SW)
        int resultadoComparacao;

        if (valorAcumulador == valorMem) {
            // se (A == Mem), define SW como 0.
            resultadoComparacao = 0;
        } else if (valorAcumulador < valorMem) {
            // se (A < Mem), define SW como -1.
            resultadoComparacao = -1;
        } else {
            // caso contrário, define SW como 1.
            resultadoComparacao = 1;
        }
        
        // Armazena o resultado da comparação no registrador Status Word (SW).
        registradores.getRegistradorPorNome("SW").setValor(resultadoComparacao);
    }
}