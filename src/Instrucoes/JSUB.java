package Instrucoes;

import java.util.Map;
import Executor.Memoria;
import Executor.Registradores;

public class JSUB extends Instruction {

    // Construtor: Define o nome e o opcode da instrução JSUB (Jump to Subroutine)
    public JSUB() {
        super("JSUB", (byte)0x48, "3/4", 3); // JSUB tem o opcode 48 em arquiteturas como SIC/XE
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));

        int enderecoRetorno = registradores.getValorPC();
        registradores.getRegistradorPorNome("L").setValorInt(enderecoRetorno); // seta L para o endereço de retorno

        registradores.getRegistradorPorNome("PC").setValorInt(TA); // seta o PC para o endereço de jump
    }
}