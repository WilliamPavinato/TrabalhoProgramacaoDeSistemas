package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class MULR extends Instruction {
    public MULR() { super("MULR", (byte)0x98, "2", 2); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int operando = memoria.getByte(pc) & 0xFF;

        int id1 = (operando >> 4) & 0xF;
        int id2 = operando & 0xF;

        registradores.incrementarPC(1);

        // Executa: R2 <- R2 * R1
        int val1 = registradores.getRegistrador(id1).getValorIntSigned();
        int val2 = registradores.getRegistrador(id2).getValorIntSigned();
        registradores.getRegistrador(id2).setValorInt(val2 * val1);
    }
}