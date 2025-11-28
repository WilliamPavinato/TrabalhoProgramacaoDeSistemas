package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SHIFTR extends Instruction {
    public SHIFTR() { super("SHIFTR", (byte)0xA8, "2", 2); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int operando = memoria.getByte(pc) & 0xFF;

        int id1 = (operando >> 4) & 0xF;
        int n   = operando & 0xF;

        registradores.incrementarPC(1);

        int val = registradores.getRegistrador(id1).getValorIntSigned();
        // Deslocamento Aritmético a Direita (Preserva sinal)
        int res = (val >> n) & 0xFFFFFF;

        registradores.getRegistrador(id1).setValorInt(res);
    }
}