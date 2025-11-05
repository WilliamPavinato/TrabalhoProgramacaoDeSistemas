package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDB extends Instruction {
    
    // Construtor: Define o nome e o opcode da instrução LDB (Load Base Register)
    public LDB() {
        super("LDB", "68"); // LDB tem o opcode 68 em arquiteturas como SIC/XE
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // 1. Obtém o endereço de memória do operando (o endereço está no PC)
        int enderecoMem = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()),16);
        
        // 2. Lê o valor (palavra) na posição de memória especificada
        // B ← (m..m+2)
        int valorMem = Integer.parseInt(memoria.getPosicaoMemoria(enderecoMem),16);
        
        // 3. Atualiza o valor do registrador Base (B) com a palavra lida
        registradores.getRegistradorPorNome("B").setValor(valorMem);
        
        // 4. Incrementa o Program Counter (PC) 
        registradores.incrementarPC();
    }
}