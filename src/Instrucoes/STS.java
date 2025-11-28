package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;
import java.util.Map;

public class STS extends Instruction {
    public STS() { super("STS", (byte)0x7C, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando
        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));
        int bytesRegA = registradores.getRegistradorPorNome("S").getValorIntSigned(); // retorna o valor armazenado no registrador S

        memoria.setWord(TA, bytesRegA); // armazena o valor do reg a na posição de memória espeçificado por TA
    }
}