package Instrucoes;

import java.util.Map;
import Executor.Memoria;
import Executor.Registradores;

public class LDCH extends Instruction {

    // Construtor: Define o nome e o opcode da instrução LDCH
    public LDCH() {
        super("LDCH", (byte)0x50, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {

        int TA = calcularTA(registradores, memoria); // operando
        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));
        else if ((!flags.get("n") && !flags.get("i")) || (flags.get("n") && flags.get("i")))
            TA = memoria.getByte(TA);

        byte[] bytesA = registradores.getRegistradorPorNome("A").getValor();
        bytesA[2] = (byte)(TA & 0xFF);

        registradores.getRegistradorPorNome("A").setValor(bytesA); // A [byte mais à direita] ← (m)
    }

}