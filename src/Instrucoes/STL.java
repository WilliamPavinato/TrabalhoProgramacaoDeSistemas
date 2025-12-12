package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;
import java.util.Map;

public class STL extends Instruction {
    public STL() { super("STL", (byte)0x14, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando
        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));
        int bytesRegA = registradores.getRegistradorPorNome("L").getValorIntSigned(); // retorna o valor armazenado no registrador L

        memoria.setWord(TA, bytesRegA); // armazena o valor do reg a na posição de memória espeçificado por TA
    }
}