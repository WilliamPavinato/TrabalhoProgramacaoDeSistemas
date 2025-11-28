package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDS extends Instruction {
    public LDS() {
        super("LDS", (byte)0x6C, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();

        // lê o endereço do operando (1 byte no arquivo do montador)
        int endereco = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1); // Consome o operando

        // busca o valor na memória (Word = 3 bytes)
        int valor = memoria.getWord(endereco);

        // salva no registrador S
        registradores.getRegistradorPorNome("S").setValorInt(valor);
    }
}