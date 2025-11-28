package Instrucoes;

import java.util.Map;
import Executor.Memoria;
import Executor.Registradores;

public class JEQ extends Instruction {

    // Construtor: Define o nome e o opcode da instrução JEQ (Jump if Equal)
    public JEQ() {
        super("JEQ",(byte)0x30, "3/4",3 );
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {

        int TA = calcularTA(registradores, memoria); // operando

        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));

        if (registradores.getRegistradorPorNome("SW").getValorIntSigned() == 0)
        {
            registradores.getRegistradorPorNome("PC").setValorInt(TA);
        }
    }
    
}