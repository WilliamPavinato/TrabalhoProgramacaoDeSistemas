package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

    // Classe abstrata base para todas as instruções
    // Garante que toda instrução tenha um nome, um opcode e um método de execução
public abstract class Instruction {
    
    private final String nome;
    private final String opcode;

    // Construtor
    Instruction(String nome, String opcode) {
        this.nome = nome;
        this.opcode = opcode;
    }


    public abstract void executar(Memoria memoria, Registradores registradores);

    // Retorna o nome mnemônico da instrução (ex: "ADD", "COMP").
    public String getNome() {
        return nome;
    }

    // Retorna o código de operação (opcode) da instrução (ex: "18", "A0").
    public String getOpcode() {
        return opcode;
    }
}