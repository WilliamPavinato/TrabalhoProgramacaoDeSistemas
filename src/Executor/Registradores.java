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

    public int getValorPC() { return registradores.get(8).getValue(); }

    public Registrador getRegistradorPorNome(String nome) {
        switch(nome) {
            case "A":  return registradores.get(0);
            case "X":  return registradores.get(1);
            case "L":  return registradores.get(2);
            case "B":  return registradores.get(3);
            case "S":  return registradores.get(4);
            case "T":  return registradores.get(5);
            case "PC": return registradores.get(8);
            case "SW": return registradores.get(9);
        }
        return null;
    }

    // Outros monstros
    public void incrementarPC() {
        registradores.get(8).incValue(1);
    }

    public void cleanRegistradores(){
        getRegistradorPorNome("A").setValue(0);
        getRegistradorPorNome("X").setValue(0);
        getRegistradorPorNome("L").setValue(0);
        getRegistradorPorNome("B").setValue(0);
        getRegistradorPorNome("S").setValue(0);
        getRegistradorPorNome("T").setValue(0);
        getRegistradorPorNome("PC").setValue(0);
    }
}
