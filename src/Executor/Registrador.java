package Executor;

public class Registrador {
    private String name;
    private int id;
    private byte[] value;


 Registrador(String name, int id, byte[] value)
 {
    this.name   = name;
    this.id     = id;
    this.value  = value;
 }

    Registrador(String nome, int id) {  // valor default é 0
        this.name   = nome;
        this.id     = id;
        this.value  = new byte[3];
        Arrays.fill(this.valor, (byte) 0);
    }

 // Getters 
 public String getNome(){ return this.name; }

 public int getId(){ return this.id; }

 public int getValor(){ return this.value; }

 public int getValorIntSigned() {
   int byte1 = valor[2];
   int byte2 = valor[1] << 8;
   int byte3 = valor[0] << 16;

   int n = byte1+byte2+byte3;
   n = (int) (n << (32 - 24)) >> (32 - 24);

   return n;
  }   

 public int getValorIntUnsigned() {
   int byte1 = valor[2];
   int byte2 = valor[1] << 8;
   int byte3 = valor[0] << 16;

   return byte1+byte2+byte3;
 }   


 // Setters
 public void setValor(byte[] newValue){
    this.value = newValue;
 }

 // For Program Counter (PC)
 public void incrementarValor(int valor){
      int pc = getValorIntSigned();
      pc += valor;
      setValorInt(pc);
    }

  public void setValorInt(int n) {
      valor[2] = (byte)((n) & 0xFF);
      valor[1] = (byte)((n >>> 8) & 0xFF);
      valor[0] = (byte)((n >>> 16) & 0xFF);
    }
}