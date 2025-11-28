package Instrucoes;

import java.util.Map;
import Executor.Memoria;
import Executor.Registradores;

/**
    Representa a instrução AND, E Lógico Bit a Bit
 */
public class AND extends Instruction {

    public AND() {
        super("AND", (byte)0x40, "3/4",3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {

        int TA = calcularTA(registradores, memoria);

        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));
        else if ((!flags.get("n") && !flags.get("i")) || (flags.get("n") && flags.get("i")))
            TA = memoria.getWord(TA);

        // Obtém o valor atual do Acumulador ('A').
        int valorAcumulador = registradores.getRegistradorPorNome("A").getValorIntSigned();

        int resultado = TA & valorAcumulador; // faz a operação AND

        registradores.getRegistradorPorNome("A").setValorInt(resultado);
    }
}