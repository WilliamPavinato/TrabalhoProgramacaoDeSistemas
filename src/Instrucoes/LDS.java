package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDS extends Instruction {
    public LDS() {
        super("LDS", (byte)0x6C, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();

        //lê apenas 1 byte para o endereço (pois o montador gerou 1 linha)
        int enderecoOperando = memoria.getByte(pc) & 0xFF; // & 0xFF converte byte negativo para int positivo

        registradores.incrementarPC(1);

        //busca o valor na memória (GetWord lê 3 bytes, ideal para variáveis inteiras)
        int valor = memoria.getWord(enderecoOperando);

        registradores.getRegistradorPorNome("S").setValorInt(valor);
    }
}