package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STL extends Instruction {
    public STL() {
        super("STL", (byte)0x14, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int endereco = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        int valor = registradores.getRegistradorPorNome("L").getValorIntSigned();
        memoria.setWord(endereco, valor);
    }
}