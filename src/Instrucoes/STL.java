package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STL extends Instruction {

    public STL() {
        super("STL", "14"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o endereço de memória (parâmetro 1) do PC (para onde vamos salvar)
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Lê e avança PC (para pular o parâmetro 'memoryAddress')
        registradores.incrementarPC();

        // Pega o valor que está no registrador "L" (Linkage)
        int registerL_Value = registradores.getRegistradorPorNome("L").getValor();

        // Converte o valor para Hexadecimal (String) para salvar na memória
        String registerL_HexValue = Integer.toHexString(registerL_Value);

        // Salva o valor do Registrador L (em Hex) no endereço de memória especificado
        memoria.setPosicaoMemoria(memoryAddress, registerL_HexValue);
    }
}