package Instrucoes;

import java.util.Map;
import Executor.Memoria;
import Executor.Registradores;

    //Compara o valor do Acumulador

public class COMP extends Instruction {

    public COMP() {
        super("COMP", (byte)0x28, "3/4",3);
    }


    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int TA = calcularTA(registradores, memoria); // operando

        Map<String, Boolean> flags = getFlags();
        if (flags.get("n") && !flags.get("i"))           // N = 1 e I = 0
            TA = memoria.getWord(memoria.getWord(TA));
        else if ((!flags.get("n") && !flags.get("i")) || (flags.get("n") && flags.get("i")))
            TA = memoria.getWord(TA);

        int valorAcumulator = registradores.getRegistradorPorNome("A").getValorIntSigned(); // valor do acumulador

        if (valorAcumulator == TA) {
            registradores.getRegistradorPorNome("SW").setValorInt(0); // SW recebe "igual", pois ValorRegA == valorMem
        } else if (valorAcumulator < TA) {
            registradores.getRegistradorPorNome("SW").setValorInt(1); // SW recebe "menor", pois ValorRegA < valorMem
        } else {
            registradores.getRegistradorPorNome("SW").setValorInt(2); // SW recebe "maior", pois ValorRegA > valorMem
        }

    }
}