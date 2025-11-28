package Instrucoes;

import java.util.Map;
import Executor.Memoria;
import Executor.Registradores;

public class LDA extends Instruction {

    // Construtor: Define o nome e o opcode da instrução LDA (Load Accumulator)
    public LDA() {
        super("LDA",(byte)0x00, "3/4", 3); // LDA tem o opcode 00 (ou simplesmente 0) em arquiteturas como SIC/XE
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando
        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));
        else if ((!flags.get("n") && !flags.get("i")) || (flags.get("n") && flags.get("i")))
            TA = memoria.getWord(TA);

        registradores.getRegistradorPorNome("A").setValorInt(TA); // seta o registrador A para o valor do operando
    }
}