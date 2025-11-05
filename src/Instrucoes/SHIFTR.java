package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SHIFTR extends Instruction {

    public SHIFTR() {
        super("SHIFTR", "A8"); // Nome e Opcode
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

        // Realiza o "shift right" (deslocamento lógico à direita)
        // Usando >>> para garantir que o bit de sinal não seja copiado, preenchendo com zeros
        int result = registerValue >>> shiftAmount;

        // Atualiza o registrador (RegA) com o resultado do shift
        registradores.getRegistrador(registerA_ID).setValor(result);

        // Avança PC para a próxima instrução
        registradores.incrementarPC();
    }
}