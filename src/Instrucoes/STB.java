package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STB extends Instruction {

    public STB() {
        super("STB", "78");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        //pega o endereço de memória onde vamos salvar (apontado pelo PC)
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança o PC para a próxima instrução
        registradores.incrementarPC();

        // pega o valor que está no Registrador "B"
        int registerB_Value = registradores.getRegistradorPorNome("B").getValor();

        // converte o valor para Hexadecimal
        String hexValue = Integer.toHexString(registerB_Value).toUpperCase();

        // salva o valor na memória no endereço especificado
        memoria.setPosicaoMemoria(memoryAddress, hexValue);
    }
}