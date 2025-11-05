package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STB extends Instruction {

    public STB() {
        super("STB", "78"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o endereço de memória (parâmetro 1) do PC (para onde vamos salvar)
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Lê e avança PC (para pular o parâmetro 'memoryAddress')
        registradores.incrementarPC();

        // Pega o valor que está no registrador "B"
        int registerB_Value = registradores.getRegistradorPorNome("B").getValor();

        // Converte o valor para Hexadecimal (String) para salvar na memóra, assumindo que sua memória armazena Strings
        String registerB_HexValue = Integer.toHexString(registerB_Value);

        // Salva o valor do Registrador B (em Hex) no endereço de memória especificado
        memoria.setPosicaoMemoria(memoryAddress, registerB_HexValue);
    }
}