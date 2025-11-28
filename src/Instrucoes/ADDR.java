package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;


    //Representa a instrução de Adição entre Registradores (ADDR).
    //Lógica: Reg_B <- Reg_A + Reg_B.

public class ADDR extends Instruction {

    public ADDR() {
        super("ADDR", (byte)0x90, "2",2); // Define nome e opcode para adição entre registradores
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {

        byte[] bytes = memoria.getBytes(registradores.getValorPC(),2); // pega dos 2 bytes que a instrução ocupa

        int[] registradoresID = getRegistradores(bytes); // id dos registradores

        int valorRegistradorA = registradores.getRegistrador(registradoresID[0]).getValorIntSigned(); // valor no reg A
        int valorRegistradorB = registradores.getRegistrador(registradoresID[1]).getValorIntSigned(); // valor no reg B

        int resultado = valorRegistradorA + valorRegistradorB;

        registradores.getRegistrador(registradoresID[1]).setValorInt(resultado);                    // seta o valor do reg B para o resultado

        registradores.incrementarPC(getFormato(memoria.getBytes(registradores.getValorPC(), 2))); // incrementa PC para a proxima instrução
    }
}