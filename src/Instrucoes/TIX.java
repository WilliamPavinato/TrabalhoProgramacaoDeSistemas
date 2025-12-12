package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;
import java.util.Map;

public class TIX extends Instruction {
    public TIX() { super("TIX", (byte)0x2C, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando
        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));
        else if ((!flags.get("n") && !flags.get("i")) || (flags.get("n") && flags.get("i")))
            TA = memoria.getWord(TA);

        int valorRegistradorX = (registradores.getRegistradorPorNome("X").getValorIntSigned()) + 1;

        registradores.getRegistradorPorNome("X").setValorInt(valorRegistradorX);

        int valorMem = memoria.getWord(TA);

        if (valorRegistradorX == valorMem) {
            registradores.getRegistradorPorNome("SW").setValorInt(0);
        } else if (valorRegistradorX < valorMem) {
            registradores.getRegistradorPorNome("SW").setValorInt(1);
        } else {
            registradores.getRegistradorPorNome("SW").setValorInt(2);
        }
    }
}