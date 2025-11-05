package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class JSUB extends Instruction {

    // Construtor: Define o nome e o opcode da instrução JSUB (Jump to Subroutine)
    public JSUB() {
        super("JSUB", "48"); // JSUB tem o opcode 48 em arquiteturas como SIC/XE
    }


    @Override
    public void executar(Memoria mem, Registradores reg) {
        // Armazenamento do Endereço de Retorno (Linkage)
        // 1. Obtém o endereço da próxima instrução (valor atual do PC)
        int enderecoRetorno = reg.getValorPC(); 
        
        // 2. Salva o endereço de retorno no Registrador L (Linkage Register)
        // L ← (PC)
        reg.getRegistradorPorNome("L").setValor(enderecoRetorno);
        
        // Desvio para a Sub-rotina
        // 3. Lê o endereço de destino do salto (o endereço da sub-rotina) da memória
        // O endereço do operando está na posição apontada pelo PC
        int enderecoJump = Integer.parseInt(mem.getPosicaoMemoria(reg.getValorPC()),16);
        
        // 4. Define o novo valor do Program Counter (PC) para o endereço da sub-rotina
        // PC ← enderecoJump
        reg.getRegistradorPorNome("PC").setValor(enderecoJump);
        
        reg.incrementarPC();
    }
}