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
    public void executar(Memoria mem, Registradores reg) {
        int TA = calcularTA(reg, mem); // operando
        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = mem.getWord(mem.getWord(TA));
        else if ((!flags.get("n") && !flags.get("i")) || (flags.get("n") && flags.get("i")))
            TA = mem.getWord(TA);

        reg.getRegistradorPorNome("A").setValorInt(TA);
    }
}