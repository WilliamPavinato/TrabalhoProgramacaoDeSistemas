package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STS extends Instruction {
    public STS() {
        super("STS", (byte)0x7C, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int endereco = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        int valor = registradores.getRegistradorPorNome("S").getValorIntSigned();
        memoria.setWord(endereco, valor);
    }
}