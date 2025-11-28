package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class RMO extends Instruction {
    public RMO() {
        super("RMO", (byte)0xAC, "2", 2);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();

        int idOrigem = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        pc = registradores.getValorPC();
        int idDestino = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        // Copia Origem -> Destino
        int valOrigem = registradores.getRegistrador(idOrigem).getValorIntSigned();
        registradores.getRegistrador(idDestino).setValorInt(valOrigem);
    }
}