package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;
import java.util.Map;

public class STCH extends Instruction {
    public STCH() { super("STCH", (byte)0x54, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando
        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));
        Byte byteA = registradores.getRegistradorPorNome("A").getValor()[2];

        memoria.setByte(TA, byteA); // guarda o primeiro byte do registrador A na posição de memória espeçificada por TA
    }
}