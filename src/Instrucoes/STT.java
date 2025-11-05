package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STT extends Instruction {

    public STT() {
        super("STT", "84"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o endereço de memória (parâmetro 1) do PC (para onde vamos salvar)
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Lê e avança PC (para pular o parâmetro 'memoryAddress')
        registradores.incrementarPC();

        // Pega o valor que está no registrador "T"
        int registerT_Value = registradores.getRegistradorPorNome("T").getValor();

        // Converte o valor para Hexadecimal (String) para salvar na memória
        String registerT_HexValue = Integer.toHexString(registerT_Value);

        // Salva o valor do Registrador T (em Hex) no endereço de memória especificado
        memoria.setPosicaoMemoria(memoryAddress, registerT_HexValue);
    }
}