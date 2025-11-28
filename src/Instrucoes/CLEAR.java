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
        byte[] bytes = memoria.getBytes(registradores.getValorPC(),2); // pega dos 2 bytes que a instrução ocupa

        int[] registradoresID = getRegistradores(bytes); // id dos registradores

        registradores.getRegistrador(registradoresID[0]).setValorInt(0); // r1 <- 0

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}