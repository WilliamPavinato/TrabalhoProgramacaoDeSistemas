package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STX extends Instruction {

    public STX() {
        super("STX", "10"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o endereço de memória (parâmetro 1) do PC (para onde vamos salvar)
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Lê e avança PC (para pular o parâmetro 'memoryAddress')
        registradores.incrementarPC();

        // Pega o valor que está no registrador "X"
        int registerX_Value = registradores.getRegistradorPorNome("X").getValor();

        // Converte o valor para Hexadecimal (String) para salvar na memória
        String registerX_HexValue = Integer.toHexString(registerX_Value);

        // Salva o valor do Registrador X (em Hex) no endereço de memória especificado
        memoria.setPosicaoMemoria(memoryAddress, registerX_HexValue);
    }
}