package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Classe abstrata base para todas as instruções
    // Garante que toda instrução tenha um nome, um opcode e um método de execução
public abstract class Instruction {
    private final String nome;
    private final byte opcode;
    Map<String, Boolean> flags = new HashMap<>();
    private final String formato;
    private final int length;

    Instruction(String nome, byte opcode, String formato, int length) {
        this.nome    = nome;
        this.opcode  = opcode;
        this.formato = formato;
        this.length  = length;
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
	flags.put("n", (bytes[0] & 0b00000010) != 0);
	flags.put("i", (bytes[0] & 0b00000001) != 0);
	flags.put("x", (bytes[1] & 0b10000000) != 0);
	flags.put("b", (bytes[1] & 0b01000000) != 0);
	flags.put("p", (bytes[1] & 0b00100000) != 0);
	flags.put("e", (bytes[1] & 0b00010000) != 0);
    }

    // Retorna estrutura de Map
    public Map<String,Boolean> getFlags()
    {
	return this.flags;
    }	

    public int getFormato(byte[] bytes) {
        setFlags(bytes);

        if(!Objects.equals(formato, "1") && !Objects.equals(formato, "2")) {
            if(!(flags.get("i") || flags.get("n"))) {
                return 3; // tipo de instrução 3
            } else if (flags.get("e")) {
                return 4; // tipo de instrução 4
            } else {
                return 3; // tipo de instrução 3
            }
        }

        return Integer.parseInt(formato); // tipo de instrução 1 ou 2
    }

    public int[] getRegistradores(byte[] bytes) {
        int[] registradores = new int[2];

        registradores[0] = (bytes[1] & 0b11110000) >>> 4; // primeiros 4 bits do segundo byte
        registradores[1] = bytes[1] & 0b00001111;// ultimos 4 bits do segundo byte

        return registradores;
    }

    public int getDisp(byte[] bytes) {
	    int byte1 = (bytes[1] & 0b00001111) << 8;
	    int byte2 = bytes[2];

	    return byte1 | byte2;
    }

    public int getDispbpe(byte[] bytes) {
	    int byte1 = (bytes[1] & 0b01111111)<<7;
        int byte2 = bytes[2] & 0xFF;

	    return byte1 | byte2;
    }

    public int getAddr(byte[] bytes) {
        int byte1 = (bytes[1] & 0b00001111) << 16;
        int byte2 = (bytes[2] & 0xFF) << 8;
        int byte3 = bytes[3]  & 0xFF;

        return byte1 | byte2 | byte3;
    }


     public int calcularTA(Registradores registradores, Memoria memoria) {
        int base =0;
        int x = 0;           // endereçamento indexado que é somado com o TA caso o flag x = 1
        int m = 0;           // onde o operando vai será armazenado
        int tamanhom = 0;
        int pc = registradores.getValorPC();  // metodo de Registradores

        setFlags(memoria.getBytes(pc, 2));

        if(!(flags.get("i") || flags.get("n"))) {
            m = getDispbpe(memoria.getBytes(pc, 3)); // tipo de instrução 3, com disp sendo os ultimos 15 bits da instrução
            tamanhom = 15;
        }
        else if (flags.get("e")) {
            m = getAddr(memoria.getBytes(pc, 4)); // tipo de instrução 4, com addr sendo os ultimos 20 bits da instrução
            tamanhom = 20;
        }
        else {
            m = getDisp(memoria.getBytes(pc, 3)); // tipo de instrução 3, com disp sendo os ultimos 12 bits da instrução
            tamanhom = 12;
        }

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2)));

        if(flags.get("b")) {
            base += registradores.getRegistradorPorNome("B").getValorIntSigned();
        }
        else if (flags.get("p")) {
            base += registradores.getValorPC();
            m = (m << (32 - tamanhom)) >> (32 - tamanhom); // extende o sinal para ter interpretado como um inteiro com sinal
        }

        // ambos são 0 caso não seja modo de endereçamento relativo a base (endereçamento direto, normalmente sendo formato de instrução 4)

        if(flags.get("x")) {
            x = registradores.getRegistradorPorNome("X").getValorIntSigned();
        }

        if(flags.get("i") && !flags.get("n")) {
            return m + base;                                        // endereçamento indexado não pode ser usado com endereçamento imediato, então não soma X
        }
        else if (flags.get("n") && !flags.get("i")) {
            return m + base;
        }

        return m + base + x;
    }
}
