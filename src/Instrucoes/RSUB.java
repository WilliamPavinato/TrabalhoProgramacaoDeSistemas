package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class RSUB extends Instruction {
    public RSUB() { super("RSUB", (byte)0x4C, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // RSUB nao tem operandos, apenas flags
        // Lê 2 bytes (ni xbpe) mas ignora deslocamento pois é retorno
        registradores.incrementarPC(2);

        int retorno = registradores.getRegistradorPorNome("L").getValorIntSigned();
        registradores.getRegistradorPorNome("PC").setValorInt(retorno);
    }
}