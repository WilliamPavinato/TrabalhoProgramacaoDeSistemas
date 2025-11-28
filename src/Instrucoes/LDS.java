package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDS extends Instruction {
    public LDS() { super("LDS", (byte)0x6C, "3/4", 3); }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        int pc = registradores.getValorPC(); // Aponta para Byte 1 (flags xbpe)

        // Lê flags e deslocamento (12 bits)
        int byte1 = memoria.getByte(pc) & 0xFF;
        int byte2 = memoria.getByte(pc + 1) & 0xFF;

        int disp = ((byte1 & 0xF) << 8) | byte2; // Pega os ultimos 4 bits do byte1 + byte2

        // Verifica Flag P (PC-Relative) - Bit 4 do byte1 (0x20)
        if ((byte1 & 0x20) != 0) {
            // Endereço = PC_atual + tamanho_instrucao + disp
            // Obs: disp é complement de 2 se negativo (simplificado aqui para positivo)
            disp += (pc + 2);
        }

        registradores.incrementarPC(2); // Avança PC

        int valor = memoria.getWord(disp);
        registradores.getRegistradorPorNome("S").setValorInt(valor);
    }
}