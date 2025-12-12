package Instrucoes;

import java.util.Map;
import Executor.Memoria;
import Executor.Registradores;

    //Realiza a divisão inteira do valor do acumulador (A) por um valor da memória e armazena o resultado inteiro no acumulador

public class DIV extends Instruction {

    public DIV() {
        super("DIV", (byte)0x24, "3/4",3); // Define nome e opcode para a operação de divisão
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

        int resultado = TA / valorAcumulator; // faz a operação de divisão

        registradores.getRegistradorPorNome("A").setValorInt(resultado); // armazena o resultado no acumulador

        // NOTA: em java, quando se divide um int por um int, o resultado vai ser um int também. Somente retorna a parte inteira da divisao
    }
}