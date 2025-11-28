package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STX extends Instruction {
    public STX() { super("STX", (byte)0x10, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int byte1 = memoria.getByte(pc) & 0xFF;
        int byte2 = memoria.getByte(pc + 1) & 0xFF;

        int disp = ((byte1 & 0xF) << 8) | byte2;

        if ((byte1 & 0x20) != 0) disp += (pc + 2);

        registradores.incrementarPC(2);

        int valorX = registradores.getRegistradorPorNome("X").getValorIntSigned();
        memoria.setWord(disp, valorX);
    }
}