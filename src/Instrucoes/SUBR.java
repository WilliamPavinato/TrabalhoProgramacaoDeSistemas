package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SUBR extends Instruction {
    public SUBR() {
        super("SUBR", (byte)0x94, "2", 2);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();

        int id1 = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        pc = registradores.getValorPC();
        int id2 = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        int val1 = registradores.getRegistrador(id1).getValorIntSigned();
        int val2 = registradores.getRegistrador(id2).getValorIntSigned();

        // R2 = R2 - R1
        registradores.getRegistrador(id2).setValorInt(val2 - val1);
    }
}