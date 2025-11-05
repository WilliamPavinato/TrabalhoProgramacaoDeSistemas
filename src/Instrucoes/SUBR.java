package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SUBR extends Instruction {

    public SUBR() {
        super("SUBR", "94"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o ID do primeiro registrador (RegA) (parâmetro 1) do PC
        int registerA_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Avança PC
        registradores.incrementarPC();

        // Pega o ID do segundo registrador (RegB) (parâmetro 2) do PC
        int registerB_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Pega o valor armazenado no primeiro registrador (RegA)
        int registerA_Value = registradores.getRegistrador(registerA_ID).getValor();

        // Pega o valor armazenado no segundo registrador (RegB)
        int registerB_Value = registradores.getRegistrador(registerB_ID).getValor();

        // RegB = RegB - RegA (A lógica é r2 = r2 - r1)
        int result = registerB_Value - registerA_Value; // Subtrai

        // O segundo registrador (RegB) recebe o resultado
        registradores.getRegistrador(registerB_ID).setValor(result); // Armazena resultado

        // Avança PC para a próxima instrução
        registradores.incrementarPC();
    }
}