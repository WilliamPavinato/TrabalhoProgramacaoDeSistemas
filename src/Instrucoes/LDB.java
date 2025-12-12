package Instrucoes;

import java.util.Map;
import Executor.Memoria;
import Executor.Registradores;

public class LDB extends Instruction {
    
    // Construtor: Define o nome e o opcode da instrução LDB (Load Base Register)
    public LDB() {
        super("LDB",(byte)0x68, "3/4", 3);// LDB tem o opcode 68 em arquiteturas como SIC/XE
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando
        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));
        else if ((!flags.get("n") && !flags.get("i")) || (flags.get("n") && flags.get("i")))
            TA = memoria.getWord(TA);

        registradores.getRegistradorPorNome("B").setValorInt(TA); // seta o registrador B para o valor do operando
    }
}