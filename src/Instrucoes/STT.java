package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STT extends Instruction {
    public STT() {
        super("STT", (byte)0x84, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int endereco = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        int valor = registradores.getRegistradorPorNome("T").getValorIntSigned();
        memoria.setWord(endereco, valor);
    }
}