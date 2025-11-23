package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STS extends Instruction {

    public STS() {
        super("STS", "7C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        //pega o endereço de memória onde vamos salvar
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança o PC para a próxima instrução
        registradores.incrementarPC();

        //pega o valor que está no Registrador "S"
        int registerS_Value = registradores.getRegistradorPorNome("S").getValor();

        //converte o valor para Hexadecimal
        String hexValue = Integer.toHexString(registerS_Value).toUpperCase();

        //salva o valor na memória no endereço especificado
        memoria.setPosicaoMemoria(memoryAddress, hexValue);
    }
}