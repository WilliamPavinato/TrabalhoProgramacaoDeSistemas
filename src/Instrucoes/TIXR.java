package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class TIXR extends Instruction {
    public TIXR() {
        super("TIXR", (byte)0xB8, "2", 2);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int idReg = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        int valorReg = registradores.getRegistrador(idReg).getValorIntSigned();
        int valorX = registradores.getRegistradorPorNome("X").getValorIntSigned() + 1;

        registradores.getRegistradorPorNome("X").setValorInt(valorX);

        if (valorX == valorReg) registradores.getRegistradorPorNome("SW").setValorInt(0);
        else if (valorX < valorReg) registradores.getRegistradorPorNome("SW").setValorInt(-1);
        else registradores.getRegistradorPorNome("SW").setValorInt(1);
    }
}