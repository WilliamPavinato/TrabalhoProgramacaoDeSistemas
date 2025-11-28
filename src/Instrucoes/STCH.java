package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STCH extends Instruction {
    public STCH() { super("STCH", (byte)0x54, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int byte1 = memoria.getByte(pc) & 0xFF;
        int byte2 = memoria.getByte(pc + 1) & 0xFF;

        int disp = ((byte1 & 0xF) << 8) | byte2;
        if ((byte1 & 0x20) != 0) disp += (pc + 2);

        registradores.incrementarPC(2);

        byte valorByte = (byte)(registradores.getRegistradorPorNome("A").getValorIntSigned() & 0xFF);
        memoria.setByte(disp, valorByte);
    }
}