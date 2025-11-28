package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SHIFTL extends Instruction {
    public SHIFTL() { super("SHIFTL", (byte)0xA4, "2", 2); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        byte[] bytes = memoria.getBytes(registradores.getValorPC(),2); // pega dos 2 bytes que a instrução ocupa
        int[] registradoresID = getRegistradores(bytes);                      //  dos registradores
        int valorRegistradorA = registradores.getRegistrador(registradoresID[0]).getValorIntSigned();
        int n = (bytes[1] & 0xFF);
        int resultado = ((valorRegistradorA << n) | (valorRegistradorA >>> (24 - n))) & 0xFFFFFF; // Deslocamento circular a esquerda

        registradores.getRegistrador(registradoresID[0]).setValorInt(resultado);
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // Próxima instrução

    }
}