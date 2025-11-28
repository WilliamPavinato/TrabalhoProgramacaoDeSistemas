package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

// Zera registrador

public class CLEAR extends Instruction {

    public CLEAR() {
        super("CLEAR", (byte)0x4, "2",2);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {

        byte[] bytes = memoria.getBytes(registradores.getValorPC(),2);

        int[] registradoresID = getRegistradores(bytes); // id dos regs

        registradores.getRegistrador(registradoresID[0]).setValorInt(0); // limpa reg
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2)));
    }
}