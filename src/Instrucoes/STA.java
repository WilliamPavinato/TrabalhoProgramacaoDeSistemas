package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STA extends Instruction {
    public STA() {
        super("STA", (byte)0x0C, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();

        // lê apenas 1 byte pra descobrir onde salvar
        int endereco = memoria.getByte(pc) & 0xFF;
        registradores.incrementarPC(1);

        // pega valor do Acumulador
        int valor = registradores.getRegistradorPorNome("A").getValorIntSigned();

        // salva na memória
        memoria.setWord(endereco, valor);
    }
}