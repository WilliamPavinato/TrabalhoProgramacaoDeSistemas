package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class ADD extends Instruction {

    public ADD() {
        super("ADD", "18"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o endereço de memória (parâmetro 1) do PC
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16); // Endereço do PC
        
        // Lê e avança PC
        registradores.incrementarPC(); 
        
        // Pega o valor armazenado na posição de memória lida
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16); // Valor da memória

        // Pega o valor que está no acumulador 
        int valorAcumulator = registradores.getRegistradorPorNome("A").getValor(); // Valor do Acumulador
        
        // acumulador += valorMem
        valorAcumulator += valorMem; // Soma

        // Acumulador recebe o resultado 
        registradores.getRegistradorPorNome("A").setValor(valorAcumulator); // Armazena resultado
    }
    
}