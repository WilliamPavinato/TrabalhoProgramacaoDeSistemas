package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STCH extends Instruction {
    public STCH() {
        super("STCH", (byte)0x54, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();
        int endereco = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        //pega apenas o último byte (8 bits) do Acumulador
        byte valorByte = (byte) (registradores.getRegistradorPorNome("A").getValorIntSigned() & 0xFF);

        //salva apenas 1 byte na memória
        memoria.setByte(endereco, valorByte);
    }
}