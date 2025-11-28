package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class RSUB extends Instruction {
    public RSUB() {
        super("RSUB", (byte)0x4C, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Recupera endereço de retorno de L
        int retorno = registradores.getRegistradorPorNome("L").getValorIntSigned();

        // Define PC para o retorno
        registradores.getRegistradorPorNome("PC").setValorInt(retorno);
    }
}