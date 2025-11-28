package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class TIX extends Instruction {
    public TIX() {
        super("TIX", (byte)0x2C, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int endereco = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        int valorMem = memoria.getWord(endereco);

        // X = X + 1
        int valorX = registradores.getRegistradorPorNome("X").getValorIntSigned() + 1;
        registradores.getRegistradorPorNome("X").setValorInt(valorX);

        // Compara e define SW (-1, 0, 1)
        if (valorX == valorMem) registradores.getRegistradorPorNome("SW").setValorInt(0);
        else if (valorX < valorMem) registradores.getRegistradorPorNome("SW").setValorInt(-1);
        else registradores.getRegistradorPorNome("SW").setValorInt(1);
    }
}