package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SHIFTR extends Instruction {
    public SHIFTR() {
        super("SHIFTR", (byte)0xA8, "2", 2);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();

        int idReg = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        pc = registradores.getValorPC();
        int n = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        int val = registradores.getRegistrador(idReg).getValorIntSigned();

        // Deslocamento Aritmético à Direita
        int res = (val >> n) & 0xFFFFFF;
        registradores.getRegistrador(idReg).setValorInt(res);
    }
}