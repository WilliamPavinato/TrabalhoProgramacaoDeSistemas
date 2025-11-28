package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;
import java.util.Map;

public class LDS extends Instruction {
    public LDS() { super("LDS", (byte)0x6C, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando
        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));
        else if ((!flags.get("n") && !flags.get("i")) || (flags.get("n") && flags.get("i")))
            TA = memoria.getWord(TA);

        registradores.getRegistradorPorNome("S").setValorInt(TA); // seta o registrador S para o valor do operando
    }
}