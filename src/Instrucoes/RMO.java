package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class RMO extends Instruction {
    public RMO() { super("RMO", (byte)0xAC, "2", 2); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC();

        // Lê o byte que contem R1 e R2
        int operando = memoria.getByte(pc) & 0xFF;

        // Extrai os IDs (4 bits superiores para R1, 4 inferiores para R2)
        int id1 = (operando >> 4) & 0xF;
        int id2 = operando & 0xF;

        registradores.incrementarPC(1); // Consome o byte de operandos

        // Executa: R2 <- R1
        int val1 = registradores.getRegistrador(id1).getValorIntSigned();
        registradores.getRegistrador(id2).setValorInt(val1);
    }
}