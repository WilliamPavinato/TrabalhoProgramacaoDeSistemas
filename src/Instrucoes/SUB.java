package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SUB extends Instruction {
    public SUB() {
        super("SUB", (byte)0x1C, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int endereco = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        int valorMem = memoria.getWord(endereco);
        int valorA = registradores.getRegistradorPorNome("A").getValorIntSigned();

        // A = A - Mem
        registradores.getRegistradorPorNome("A").setValorInt(valorA - valorMem);
    }
}