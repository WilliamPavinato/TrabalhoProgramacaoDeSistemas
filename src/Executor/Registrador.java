package Executor;

public class Registrador {
    private String name;
    private int id;
    private int value;


 Registrador(String name, int id, int value)
 {
    this.name   = name;
    this.id     = id;
    this.value  = value;
 }

    Registrador(String nome, int id) {  // valor default é 0
        this.name   = nome;
        this.id     = id;
        this.value  = 0;
    }

 // Getters 
 public String getName(){ return this.name; }

 public int getId(){ return this.id; }

 public int getValue(){ return this.value; }


 // Setters
 public void setValue(int newValue){
    this.value = newValue;
 }

 // Other methods

 // For Program Counter (PC)
 public void incValue(int incValue){
    this.value += incValue;
 }
}