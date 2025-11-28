package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STB extends Instruction {
    public STB() {
        super("STB", (byte)0x78, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int endereco = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        int valor = registradores.getRegistradorPorNome("B").getValorIntSigned();
        memoria.setWord(endereco, valor);
    }
}