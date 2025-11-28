package Executor;

import java.util.Arrays;

public class Memoria {

    private final byte[] memoria;
    private final int capacidade;

    Memoria(int capacidade) {
        this.memoria = new byte[capacidade];
        this.capacidade = capacidade;
    }

    //------------------

    public byte[] getMemoria() { return memoria; }

    public void limparMemoria() { Arrays.fill(memoria, (byte)0); }

    // Manipula Bytes

    public byte getByte(int posicao) { return (byte)((memoria[posicao]) & 0xFF); }

    public byte[] getBytes(int posicao, int numero) {
        byte[] bytes = new byte[numero];
        for(int i = 0; i<numero && posicao+i <= capacidade; i++) {
            bytes[i] = getByte(posicao+i);
        }
        return bytes;
    }

    public void setByte(int posicao,byte b) { memoria[posicao] = b; }

    // Seta o byte no address para o inteiro informado
    public void setByteInt(int posicao, int value) { memoria[posicao] = (byte)(value & 0xFF); }

    // Get Opcode
    public byte getOpcode(int posicao) {
        byte primeiroByte = getByte(posicao);
        return (byte)((primeiroByte & 0b11111100));
    }

    // Manipula palavras de memoria

    public void setWord(int posicao, int value) {
        setByteInt(posicao, value >>> 16);
        setByteInt(posicao + 1, value >>> 8);
        setByteInt(posicao + 2, value);
    }
    
    public int getWord(int posicao) {
        int MSB = getByte(posicao) << 16; // shiftL 16 bits
        int MID = getByte(posicao + 1) << 8;  // shiftL 8b its
        int LSB = getByte(posicao + 2);


        return MSB + MID + LSB;
    }

}