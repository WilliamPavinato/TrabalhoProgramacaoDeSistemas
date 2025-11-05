package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STCH extends Instruction {

    public STCH() {
        super("STCH", "54"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o endereço de memória (parâmetro 1) do PC (para onde vamos salvar)
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Lê e avança PC (para pular o parâmetro 'memoryAddress')
        registradores.incrementarPC();

        // Obtém o valor do registrador "A"
        int accumulatorValue = registradores.getRegistradorPorNome("A").getValor();

        // Obtém o byte menos significativo (os 8 bits da direita) do valor de "A"
        // STCH armazena apenas 1 byte (o caracter)
        int leastSignificantByte = accumulatorValue & 0xFF;

        // Converte o byte para Hexadecimal (String)
        String byteHexValue = Integer.toHexString(leastSignificantByte);

        // Armazena o byte (em Hex) na posição de memória especificada
        memoria.setPosicaoMemoria(memoryAddress, byteHexValue);
    }
}