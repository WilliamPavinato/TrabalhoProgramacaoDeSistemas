package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class SUBR extends Instruction {

    public SUBR() {
        super("SUBR", "94");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        //pega o ID do primeiro registrador (Reg1) apontado pelo PC
        int reg1_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança PC para ler o próximo parâmetro
        registradores.incrementarPC();

        // pega o ID do segundo registrador (Reg2) apontado pelo PC
        int reg2_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança PC para a próxima instrução
        registradores.incrementarPC();

        // busca os valores dentro desses registradores
        int valReg1 = registradores.getRegistrador(reg1_ID).getValor();
        int valReg2 = registradores.getRegistrador(reg2_ID).getValor();

        // realiza a subtração

        int resultado = valReg2 - valReg1; // R2 = R2 - R1

        // salva o resultado no segundo registrador (Reg2)
        registradores.getRegistrador(reg2_ID).setValor(resultado);
    }
}