package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SHIFTL extends Instruction {

    public SHIFTL() {
        super("SHIFTL", "A4"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o ID do registrador (RegA) (parâmetro 1) do PC
        int registerA_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Avança PC
        registradores.incrementarPC();

        // Pega a quantidade de bits para o shift (parâmetro 2) do PC
        int shiftAmount = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Pega o valor armazenado no registrador (RegA)
        int registerValue = registradores.getRegistrador(registerA_ID).getValor();

        // Realiza o "shift left" (deslocamento à esquerda)
        // (Isso é equivalente a multiplicar por 2^shiftAmount)
        int result = registerValue << shiftAmount;

        // Atualiza o registrador (RegA) com o resultado do shift
        registradores.getRegistrador(registerA_ID).setValor(result);

        // Avança PC para a próxima instrução
        registradores.incrementarPC();
    }
}