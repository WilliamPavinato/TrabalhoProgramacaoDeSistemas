package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SHIFTR extends Instruction {

    public SHIFTR() {
        super("SHIFTR", "A8");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // pega o ID do registrador que será deslocado (pparâmetro 1)
        int registerID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança o PC para ler o próximo parâmetro (o 'n')
        registradores.incrementarPC();

        // pega a quantidade de deslocamento 'n' (parâmetro 2)
        int n = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança o PC para a próxima instrução
        registradores.incrementarPC();

        //busca o valor atual do registrador
        int registerValue = registradores.getRegistrador(registerID).getValor();

        //realiza o Deslocamento Aritmético à Direita (Preserva o sinal), usa a máscara 0xFFFFFF para garantir que fique dentro dos 24 bits
        int resultado = (registerValue >> n) & 0xFFFFFF;

        //salva o resultado de volta no registrador
        registradores.getRegistrador(registerID).setValor(resultado);
    }
}