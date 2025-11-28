package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class OR extends Instruction {
    public OR() {
        super("OR", (byte)0x44, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int endereco = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        int valorMem = memoria.getWord(endereco);
        int valorA = registradores.getRegistradorPorNome("A").getValorIntSigned();

        // A = A | Mem
        registradores.getRegistradorPorNome("A").setValorInt(valorA | valorMem);
    }
}