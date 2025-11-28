package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SHIFTR extends Instruction {
    public SHIFTR() { super("SHIFTR", (byte)0xA8, "2", 2); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        byte[] bytes = memoria.getBytes(registradores.getValorPC(),2); // pega dos 2 bytes que a instrução ocupa
        int[] registradoresID = getRegistradores(bytes);                      // ID dos registradores
        int valorRegistradorA = registradores.getRegistrador(registradoresID[0]).getValorIntSigned() + 1;
        int n = (bytes[1] & 0xFF);
        int resultado = ((valorRegistradorA >> n) & 0xFFFFFF); // Deslocamento circular a direita preservando o sinal

        registradores.getRegistrador(registradoresID[0]).setValorInt(resultado);
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // Próxima instrução
    }
}