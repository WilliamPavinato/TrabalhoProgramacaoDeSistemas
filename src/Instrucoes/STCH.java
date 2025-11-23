package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class STCH extends Instruction {

    public STCH() {
        super("STCH", "54");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // pega o endereço de memória onde vamos salvar (apontado pelo PC)
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança o PC para a próxima instrução
        registradores.incrementarPC();

        // obtém o valor completo do registrador "A"
        int accumulatorValue = registradores.getRegistradorPorNome("A").getValor();

        //obtém apenas o byte menos significativo, é equivalente a pegar o caractere ASCII armazenado
        int leastSignificantByte = accumulatorValue & 0xFF;

        //converte o byte para Hexadecimal
        String byteHexValue = Integer.toHexString(leastSignificantByte).toUpperCase();

        // opcional - garante formatação de 2 dígitos se necessário (ex: "A" vira "0A")
        if (byteHexValue.length() == 1) {
            byteHexValue = "0" + byteHexValue;
        }

        //armazena esse único byte na posição de memória especificada
        memoria.setPosicaoMemoria(memoryAddress, byteHexValue);
    }
}