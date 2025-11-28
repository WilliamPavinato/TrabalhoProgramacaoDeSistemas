package Executor;

import java.util.Arrays;

public class Registrador {
    private final String name;
    private final int id;
    private byte[] value;


    Registrador(String name, int id, byte[] value) {
       this.name   = name;
       this.id     = id;
       this.value  = value;
    }

    Registrador(String nome, int id) {  // valor default é 0
        this.name   = nome;
        this.id     = id;
        this.value  = new byte[3];
        Arrays.fill(this.value, (byte) 0);
    }

    // Getters
    public String getNome()  { return this.name; }

    public int getId()       { return this.id; }

    public byte[] getValor() { return this.value; }

    public int getValorIntSigned() {
        int byte1 = value[2];
        int byte2 = value[1] << 8;
        int byte3 = value[0] << 16;

        int n = byte1+byte2+byte3;
        n = (n << (32 - 24)) >> (32 - 24);
        return n;
     }

    public int getValorIntUnsigned() {
        int byte1 = value[2];
        int byte2 = value[1] << 8;
        int byte3 = value[0] << 16;

        return byte1 + byte2 + byte3;
    }

    // Setters
    public void setValor(byte[] newValue) { this.value = newValue; }

    // For Program Counter (PC)
    public void incrementarValor(int valor){
        int pc = getValorIntSigned();
        pc += valor;
        setValorInt(pc);
    }

    public void setValorInt(int n) {
        value[2] = (byte)((n) & 0xFF);
        value[1] = (byte)((n >>> 8) & 0xFF);
        value[0] = (byte)((n >>> 16) & 0xFF);
    }
}
