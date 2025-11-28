package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STT extends Instruction {
    public STT() { super("STT", (byte)0x84, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int byte1 = memoria.getByte(pc) & 0xFF;
        int byte2 = memoria.getByte(pc + 1) & 0xFF;

        int disp = ((byte1 & 0xF) << 8) | byte2;

        if ((byte1 & 0x20) != 0) disp += (pc + 2);

        registradores.incrementarPC(2);

        int valorT = registradores.getRegistradorPorNome("T").getValorIntSigned();
        memoria.setWord(disp, valorT);
    }
}