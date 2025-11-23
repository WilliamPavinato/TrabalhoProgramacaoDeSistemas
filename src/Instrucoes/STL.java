package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STL extends Instruction {

    public STL() {
        super("STL", "14");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // pega o endereço de memória onde vamos salvar
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança o PC para a próxima instrução
        registradores.incrementarPC();

        //pega o valor que está no Registrador "L"
        int registerL_Value = registradores.getRegistradorPorNome("L").getValor();

        // converte o valor para Hexadecimal
        String hexValue = Integer.toHexString(registerL_Value).toUpperCase();

        //salva o valor na memória no endereço especificado
        memoria.setPosicaoMemoria(memoryAddress, hexValue);
    }
}