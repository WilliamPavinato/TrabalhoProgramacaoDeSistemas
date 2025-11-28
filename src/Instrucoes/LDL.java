package Instrucoes;

import java.util.Map;
import Executor.Memoria;
import Executor.Registradores;

public class LDL extends Instruction {

    // Construtor: Define o nome e o opcode da instrução LDL
    public LDL() {
        super("LDL",(byte)0x34, "3/4", 3); // LDL tem o opcode 08
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria);

        Map<String, Boolean> flags = getFlags();

        if (flags.get("n") && !flags.get("i"))
            TA = memoria.getWord(memoria.getWord(TA));
        if (registradores.getRegistradorPorNome("SW").getValorIntSigned() == 2)
        {
            registradores.getRegistradorPorNome("PC").setValorInt(TA); // pc <- jump addrs
        }
    }

}