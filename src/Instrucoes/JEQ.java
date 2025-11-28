package Instrucoes;

import java.util.Map;
import Executor.Memoria;
import Executor.Registradores;

public class JEQ extends Instruction {

    // Construtor: Define o nome e o opcode da instrução JEQ (Jump if Equal)
    public JEQ() {
        super("JEQ",(byte)0x30, "3/4",3 );
    }

    @Override
    public void executar(Memoria mem, Registradores reg) {
        int TA = calcularTA(reg, mem); // operando

        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = mem.getWord(mem.getWord(TA));

        if (reg.getRegistradorPorNome("SW").getValorIntSigned() == 0)
        {
            reg.getRegistradorPorNome("PC").setValorInt(TA);
        }
    }
    
}