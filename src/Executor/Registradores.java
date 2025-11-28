package Executor;

import java.util.HashMap;
import java.util.Map;

public class Registradores {
    private final Map<Integer, Registrador> registradores;

    Registradores(){
        Map<Integer, Registrador> regs = new HashMap<>();

        regs.put(0, new Registrador("A", 0)); // Acumulador - Armazena os dados (carregados e resultantes) das operações da Unid. de Lógica e Aritmética
        regs.put(1, new Registrador("X",1));  // Índice - Usado para endereçamento.
        regs.put(2, new Registrador("L",2));  // Ligação - A instrução Jump to Subrotine (JSUB) armazena o endereço de retorno nesse registrador.
        regs.put(3, new Registrador("B",3));  // Base - Para endereçamento.
        regs.put(4, new Registrador("S",4));  // Uso Geral
        regs.put(5, new Registrador("T",5));  // Uso Geral
        regs.put(8, new Registrador("PC",8)); // Program Counter - Guarda o ndereço da próxima instrução a ser executada
        regs.put(9, new Registrador("SW",9)); // Status Word - Contém várias informações, incluindo código condicional (CC)
        // -1 - <
        //  0 - =
        //  1 - >

        registradores = regs;
    }

    // Getters
    public Registrador getRegistrador(int id) { return registradores.get(id); }

    public Registrador getRegistradorPorNome(String nome) {
        return switch (nome) {
            case "A"  -> registradores.get(0);
            case "X"  -> registradores.get(1);
            case "L"  -> registradores.get(2);
            case "B"  -> registradores.get(3);
            case "S"  -> registradores.get(4);
            case "T"  -> registradores.get(5);
            case "PC" -> registradores.get(8);
            case "SW" -> registradores.get(9);
            default   -> null;
        };
    }

    public static int getChaveRegistradorPorNome(String nome) {
        return switch (nome) {
            case "A"  -> 0;
            case "X"  -> 1;
            case "L"  -> 2;
            case "B"  -> 3;
            case "S"  -> 4;
            case "T"  -> 5;
            case "PC" -> 8;
            case "SW" -> 9;
            default   -> -1;
        };
    }

    public void incrementarPC(int valor) { registradores.get(8).incrementarValor(valor); }
    public int getValorPC()              { return registradores.get(8).getValorIntSigned(); }
    public void cleanRegistradores(){
        getRegistradorPorNome("A").setValorInt(0);
        getRegistradorPorNome("X").setValorInt(0);
        getRegistradorPorNome("L").setValorInt(0);
        getRegistradorPorNome("B").setValorInt(0);
        getRegistradorPorNome("S").setValorInt(0);
        getRegistradorPorNome("T").setValorInt(0);
        getRegistradorPorNome("PC").setValorInt(0);
    }
}
