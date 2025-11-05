package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

    //Realiza a divisão inteira do valor do acumulador (A) por um valor da memória e armazena o resultado inteiro no acumulador

public class DIV extends Instruction {

    public DIV() {
        super("DIV", "24"); // Define nome e opcode para a operação de divisão
    }



    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o endereço de memória (divisor) apontado pelo PC
        String strEndereco = memoria.getPosicaoMemoria(registradores.getValorPC());
        int enderecoMem = Integer.parseInt(strEndereco, 16); // Converte de hex para int

        // Avança o Program Counter (PC) para a próxima instrução.
        registradores.incrementarPC();

        // Pega o valor armazenado na posição de memória lida (o divisor)
        String strValorMemoria = memoria.getPosicaoMemoria(enderecoMem);
        int valorDivisor = Integer.parseInt(strValorMemoria, 16); // Converte de hex para int

        // Obtém o valor atual do acumulador, que é o dividendo
        int valorDividendo = registradores.getRegistradorPorNome("A").getValor();

        try 
        {
            int resultadoDivisao = valorDividendo / valorDivisor;
            registradores.getRegistradorPorNome("A").setValor(resultadoDivisao);
        }
        catch (ArithmeticException e) {
            // Se houver divisão por zero, o valor do acumulador é mantido inalterado
        }
    }
}