package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class TIXR extends Instruction {
    public TIXR() { super("TIXR", (byte)0xB8, "2", 2); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        byte[] bytes = memoria.getBytes(registradores.getValorPC(),2); // pega dos 2 bytes que a instrução ocupa
        int[] registradoresID = getRegistradores(bytes); // id dos registradores
        int valorRegistradorA = registradores.getRegistrador(registradoresID[0]).getValorIntSigned(); // valor no reg A
        int valorRegistradorX = (registradores.getRegistradorPorNome("X").getValorIntSigned()) + 1;

        registradores.getRegistradorPorNome("X").setValorInt(valorRegistradorX);

        if (valorRegistradorX == valorRegistradorA) {
            registradores.getRegistradorPorNome("SW").setValorInt(0);
        } else if (valorRegistradorX < valorRegistradorA) {
            registradores.getRegistradorPorNome("SW").setValorInt(1);
        } else {
            registradores.getRegistradorPorNome("SW").setValorInt(2);
        }

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}