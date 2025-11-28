package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;
import java.util.Map;

public class MUL extends Instruction {
    public MUL() { super("MUL", (byte)0x20, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando
        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));
        else if ((!flags.get("n") && !flags.get("i")) || (flags.get("n") && flags.get("i")))
            TA = memoria.getWord(TA);

        int valorAcumulator = registradores.getRegistradorPorNome("A").getValorIntSigned(); // valor do acumulador

        int resultado = TA * valorAcumulator; // faz a multiplicação

        registradores.getRegistradorPorNome("A").setValorInt(resultado); // armazena o resultado no acumulador
    }
}