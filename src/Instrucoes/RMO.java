package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class RMO extends Instruction {

    public RMO() {
        super("RMO", "AC");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // pega o ID do registrador de ORIGEM (r1) apontado pelo PC
        int r1_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança PC para o próximo parâmetro
        registradores.incrementarPC();

        // pega o ID do registrador de DESTINO (r2) apontado pelo PC
        int r2_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança PC para a próxima instrução
        registradores.incrementarPC();

        // busca o valor que está no registrador de origem (r1)
        int valueR1 = registradores.getRegistrador(r1_ID).getValor();

        // copia esse valor para o registrador de destino (r2)
        registradores.getRegistrador(r2_ID).setValor(valueR1); // R2 <- R1
    }
}