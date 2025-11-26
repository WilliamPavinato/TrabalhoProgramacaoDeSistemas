package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

    // Classe abstrata base para todas as instruções
    // Garante que toda instrução tenha um nome, um opcode e um método de execução
public abstract class Instruction {
    
    private final String nome;
    private final byte opcode;
    Map<String, Boolean> flags = new HashMap<>();
    private final String formato;
    private final int length;

 
    Instruction(String nome, byte opcode, String formato, int length) {
        this.nome = nome;
        this.opcode = opcode;
        this.formato = formato;
        this.length = length;
    }


    public abstract void executar(Memoria memoria, Registradores registradores);

    public String getNome() 
    {
        return nome;
    }

    public byte getOpcode() 
    {
        return opcode;
    }

    public String getFormato() 
    {
        return formato;
    }

    public int getLength() 
    {
        return length;
    }

    public void setFlags(byte[] bytes)
    {

    }
}