package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STA extends Instruction {

    public STA() {
        super("STA", "0C"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o endereço de memória (parâmetro 1) do PC (para onde vamos salvar)
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Lê e avança PC (para pular o parâmetro 'memoryAddress')
        registradores.incrementarPC();

        // Pega o valor que está no acumulador "A"
        int accumulatorValue = registradores.getRegistradorPorNome("A").getValor();

        // Converte o valor para Hexadecimal (String) para salvar na memória, assumindo que sua memória armazena Strings
        String accumulatorHexValue = Integer.toHexString(accumulatorValue);

        // Salva o valor do Acumulador (em Hex) no endereço de memória especificado
        memoria.setPosicaoMemoria(memoryAddress, accumulatorHexValue);
    }
}