package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STS extends Instruction {

    public STS() {
        super("STS", "7C"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o endereço de memória (parâmetro 1) do PC (para onde vamos salvar)
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Lê e avança PC (para pular o parâmetro 'memoryAddress')
        registradores.incrementarPC();

        // Pega o valor que está no registrador "S"
        int registerS_Value = registradores.getRegistradorPorNome("S").getValor();

        // Converte o valor para Hexadecimal (String) para salvar na memória
        String registerS_HexValue = Integer.toHexString(registerS_Value);

        // Salva o valor do Registrador S (em Hex) no endereço de memória especificado
        memoria.setPosicaoMemoria(memoryAddress, registerS_HexValue);
    }
}