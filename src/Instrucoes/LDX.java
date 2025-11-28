package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDX extends Instruction {
    public LDX() {
        super("LDX", (byte)0x04, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int endereco = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        int valor = memoria.getWord(endereco);
        registradores.getRegistradorPorNome("X").setValorInt(valor);
    }
}