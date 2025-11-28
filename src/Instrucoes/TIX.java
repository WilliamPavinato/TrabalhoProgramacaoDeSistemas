package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class TIX extends Instruction {
    public TIX() { super("TIX", (byte)0x2C, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int byte1 = memoria.getByte(pc) & 0xFF;
        int byte2 = memoria.getByte(pc + 1) & 0xFF;

        int disp = ((byte1 & 0xF) << 8) | byte2;
        if ((byte1 & 0x20) != 0) disp += (pc + 2);

        registradores.incrementarPC(2);

        int valorMem = memoria.getWord(disp);
        int valorX = registradores.getRegistradorPorNome("X").getValorIntSigned() + 1;
        registradores.getRegistradorPorNome("X").setValorInt(valorX);

        if (valorX == valorMem) registradores.getRegistradorPorNome("SW").setValorInt(0);
        else if (valorX < valorMem) registradores.getRegistradorPorNome("SW").setValorInt(-1);
        else registradores.getRegistradorPorNome("SW").setValorInt(1);
    }
}