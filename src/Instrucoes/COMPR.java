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
        byte[] bytes = memoria.getBytes(registradores.getValorPC(),2); // pega dos 2 bytes que a instrução ocupa

        int[] registradoresID = getRegistradores(bytes); // id dos registradores

        int valorRegistradorA = registradores.getRegistrador(registradoresID[0]).getValorIntSigned(); // valor no reg A
        int valorRegistradorB = registradores.getRegistrador(registradoresID[1]).getValorIntSigned(); // valor no reg B

        if (valorRegistradorA == valorRegistradorB) {
            registradores.getRegistradorPorNome("SW").setValorInt(0); // SW recebe "igual", pois ValorRegA == valorRegB
        } else if (valorRegistradorA < valorRegistradorB) {
            registradores.getRegistradorPorNome("SW").setValorInt(1); // SW recebe "menor", pois ValorRegA == valorRegB
        } else {
            registradores.getRegistradorPorNome("SW").setValorInt(2); // SW recebe "maior", pois ValorRegA == valorRegB
        }

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}