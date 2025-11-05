package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

/**
    Representa a instrução AND, E Lógico Bit a Bit
 */
public class AND extends Instruction {

    public AND() {
        super("AND", "40"); // Define nome e opcode para a operação AND
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {

        // Pega o endereço de memória (parâmetro 1) apontado pelo PC.
        String strEndereco = memoria.getPosicaoMemoria(registradores.getValorPC());
        int enderecoMem = Integer.parseInt(strEndereco, 16); // Converte de hex para int

        // Avança o Program Counter (PC) para a próxima instrução.
        registradores.incrementarPC();

        // Pega o valor armazenado na posição de memória lida (o dado).
        String strValorMemoria = memoria.getPosicaoMemoria(enderecoMem);
        int valorMem = Integer.parseInt(strValorMemoria, 16); // Converte de hex para int

        // Obtém o valor atual do Acumulador ('A').
        int valorAcumulador = registradores.getRegistradorPorNome("A").getValor();

        // Executa a operação AND lógica bit a bit
        int resultadoAND = valorAcumulador & valorMem;

        // Armazena o resultado de volta no Acumulador 
        registradores.getRegistradorPorNome("A").setValor(resultadoAND);
    }
}