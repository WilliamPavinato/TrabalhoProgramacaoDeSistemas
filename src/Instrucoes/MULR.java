package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class MULR extends Instruction {

    public MULR() {
        super("MULR", "98"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o ID do primeiro registrador (parâmetro 1) do PC
        int registerA_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Avança PC
        registradores.incrementarPC();

        // Pega o ID do segundo registrador (parâmetro 2) do PC
        int registerB_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Avança PC
        registradores.incrementarPC();

        // Pega o valor armazenado no primeiro registrador (RegA)
        int registerA_Value = registradores.getRegistrador(registerA_ID).getValue();

        // Pega o valor armazenado no segundo registrador (RegB)
        int registerB_Value = registradores.getRegistrador(registerB_ID).getValue();

        // RegB = RegA * RegB (A lógica é r2 = r1 * r2)
        int result = registerA_Value * registerB_Value; // Multiplica

        // O segundo registrador (RegB) recebe o resultado
        registradores.getRegistrador(registerB_ID).setValor(result); // Armazena resultado
    }

}