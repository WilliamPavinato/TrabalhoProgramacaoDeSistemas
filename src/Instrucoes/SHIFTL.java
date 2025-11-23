package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SHIFTL extends Instruction {

    public SHIFTL() {
        super("SHIFTL", "A4");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // pega o ID do registrador que será deslocado (parâmetro 1)
        int registerID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança o PC para ler o próximo parâmetro (o 'n')
        registradores.incrementarPC();

        // pega a quantidade de deslocamento 'n' (parâmetro 2)
        int n = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança o PC para a próxima instrução
        registradores.incrementarPC();

        // busca o valor atual do registrador
        int registerValue = registradores.getRegistrador(registerID).getValor();

        // realiza o Deslocamento Circular à Esquerda (Rotate Left) em 24 bits
        int resultado = ((registerValue << n) | (registerValue >>> (24 - n))) & 0xFFFFFF;

        //salva o resultado de volta no registrador
        registradores.getRegistrador(registerID).setValor(resultado);
    }
}