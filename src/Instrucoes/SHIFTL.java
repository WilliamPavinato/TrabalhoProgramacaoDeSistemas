package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SHIFTL extends Instruction {
    public SHIFTL() {
        super("SHIFTL", (byte)0xA4, "2", 2);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();

        // Lê ID do Registrador
        int idReg = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        // Lê quantidade de bits (n)
        pc = registradores.getValorPC();
        int n = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        int val = registradores.getRegistrador(idReg).getValorIntSigned();

        // Deslocamento Circular à Esquerda
        int res = ((val << n) | (val >>> (24 - n))) & 0xFFFFFF;
        registradores.getRegistrador(idReg).setValorInt(res);
    }
}