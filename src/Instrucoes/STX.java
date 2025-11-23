package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STX extends Instruction {

    public STX() {
        super("STX", "10");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        //pega o endereço de memória onde vamos salvar
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança o PC para a próxima instrução
        registradores.incrementarPC();

        //pega o valor que está no registrador "X"
        int registerX_Value = registradores.getRegistradorPorNome("X").getValor();

        //converte o valor para Hexadecimal
        String hexValue = Integer.toHexString(registerX_Value).toUpperCase();

        //salva o valor na memória no endereço especificado
        memoria.setPosicaoMemoria(memoryAddress, hexValue);
    }
}