package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class TIXR extends Instruction {
    public TIXR() { super("TIXR", (byte)0xB8, "2", 2); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int operando = memoria.getByte(pc) & 0xFF;

        // TIXR R1 (R1 está nos primeiros 4 bits)
        int id1 = (operando >> 4) & 0xF;

        registradores.incrementarPC(1);

        // X <- X + 1
        int valX = registradores.getRegistradorPorNome("X").getValorIntSigned() + 1;
        registradores.getRegistradorPorNome("X").setValorInt(valX);

        // Compara X com R1
        int valR1 = registradores.getRegistrador(id1).getValorIntSigned();

        if (valX == valR1) registradores.getRegistradorPorNome("SW").setValorInt(0);
        else if (valX < valR1) registradores.getRegistradorPorNome("SW").setValorInt(-1);
        else registradores.getRegistradorPorNome("SW").setValorInt(1);
    }
}