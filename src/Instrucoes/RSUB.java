package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class RSUB extends Instruction {
    public RSUB() { super("RSUB", (byte)0x4C, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        byte[] valorL = registradores.getRegistradorPorNome("L").getValor();    // Obtém o valor do reg L
        registradores.getRegistradorPorNome("PC").setValor(valorL);             // PC fica com o valor do registrador L
        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // Próxima instrução
    }
}