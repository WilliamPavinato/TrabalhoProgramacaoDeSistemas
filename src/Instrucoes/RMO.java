package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class RMO extends Instruction {
    public RMO() { super("RMO", (byte)0xAC, "2", 2); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        byte[] bytes = memoria.getBytes(registradores.getValorPC(),2); // pega dos 2 bytes que a instrução ocupa
        int[] registradoresID = getRegistradores(bytes); // id dos registradores
        int valorRegistradorB = registradores.getRegistrador(registradoresID[1]).getValorIntSigned(); // valor no reg B
        registradores.getRegistrador(registradoresID[0]).setValorInt(valorRegistradorB);
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}