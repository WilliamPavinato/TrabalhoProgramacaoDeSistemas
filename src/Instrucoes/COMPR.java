package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;


// Compara o valor do Registrador A com o valor do Registrador B e define o registrador

public class COMPR extends Instruction {

    public COMPR() {
        super("COMPR", (byte)0xA0, "2",2);
    }


    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        byte[] bytes = memoria.getBytes(registradores.getValorPC(),2); // pega 2 B

        int[] registradoresID = getRegistradores(bytes); // id dos registradores

        int valorRegistradorA = registradores.getRegistrador(registradoresID[0]).getValorIntSigned(); // reg A
        int valorRegistradorB = registradores.getRegistrador(registradoresID[1]).getValorIntSigned(); // reg B

        if (valorRegistradorA == valorRegistradorB) {
            registradores.getRegistradorPorNome("SW").setValorInt(0);
        } else if (valorRegistradorA < valorRegistradorB) {
            registradores.getRegistradorPorNome("SW").setValorInt(1);
        } else {
            registradores.getRegistradorPorNome("SW").setValorInt(2); 
        }

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}