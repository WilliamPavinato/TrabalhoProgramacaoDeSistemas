package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SHIFTL extends Instruction {
    public SHIFTL() { super("SHIFTL", (byte)0xA4, "2", 2); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int operando = memoria.getByte(pc) & 0xFF;

        // Formato: R1 (4 bits) | n (4 bits)
        int id1 = (operando >> 4) & 0xF;
        int n   = operando & 0xF; // Quantidade de bits a deslocar

        registradores.incrementarPC(1);

        int val = registradores.getRegistrador(id1).getValorIntSigned();
        // Deslocamento Circular a Esquerda (24 bits)
        int res = ((val << n) | (val >>> (24 - n))) & 0xFFFFFF;

        registradores.getRegistrador(id1).setValorInt(res);
    }
}