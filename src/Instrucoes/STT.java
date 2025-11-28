package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;
import java.util.Map;

public class STT extends Instruction {
    public STT() { super("STT", (byte)0x84, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando
        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));
        int bytesRegA = registradores.getRegistradorPorNome("T").getValorIntSigned(); // retorna o valor armazenado no registrador T

        memoria.setWord(TA, bytesRegA); // armazena o valor do reg a na posição de memória espeçificado por TA
    }
}