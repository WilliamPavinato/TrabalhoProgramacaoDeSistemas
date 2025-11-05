package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

    //Realiza a divisão inteira (Reg_A / Reg_B) e armazena o quociente (parte inteira) no reg B

public class DIVR extends Instruction {

    public DIVR() {
        super("DIVR", "9C"); // Define nome e opcode para divisão entre registradores
    }



    @Override
    public void executar(Memoria memoria, Registradores registradores) {
       
        // Obtém o ID do primeiro registrador reg A a partir do PC
        String strIdRegA = memoria.getPosicaoMemoria(registradores.getValorPC());
        int idRegistradorA = Integer.parseInt(strIdRegA, 16); // Converte ID de hexadecimal para inteiro
        registradores.incrementarPC(); // Avança o PC

        // Obtém o ID do segundo registrador reg B a partir do PC.
        String strIdRegB = memoria.getPosicaoMemoria(registradores.getValorPC());
        int idRegistradorB = Integer.parseInt(strIdRegB, 16); // Converte ID de hexadecimal para inteiro
        registradores.incrementarPC(); // Avança o PC

        // reg A é o dividendo, reg B é o divisor
        int valorDividendo = registradores.getRegistrador(idRegistradorA).getValor();
        int valorDivisor = registradores.getRegistrador(idRegistradorB).getValor();

       try 
       {
            int quociente = valorDividendo / valorDivisor;
            // Reg_B <- Reg_A / Reg_B
            registradores.getRegistrador(idRegistradorB).setValor(quociente);
        } 
        catch (ArithmeticException e) {
            // Se houver divisão por zero, o valor do Registrador B é mantido inalterado
        }
    }
}