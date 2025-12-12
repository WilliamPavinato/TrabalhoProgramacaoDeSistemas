package Instrucoes;

import java.util.Map;
import Executor.Memoria;
import Executor.Registradores;

public class JLT extends Instruction {

    // Construtor: Define o nome e o opcode da instrução JLT (Jump if Less Than)
    public JLT() {
        super("JLT",  (byte)0x38, "3/4", 3);// JLT tem o opcode 38 em arquiteturas como SIC/XE
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));

        if (registradores.getRegistradorPorNome("SW").getValorIntSigned() == 1)
        {
            registradores.getRegistradorPorNome("PC").setValorInt(TA); // seta o PC para o endereço de jump
        }
    }
}