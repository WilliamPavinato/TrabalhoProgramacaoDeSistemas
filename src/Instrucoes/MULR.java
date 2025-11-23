package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class MULR extends Instruction {

    public MULR() {
        super("MULR", "98");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // pega o ID do primeiro registrador (Reg 1) apontado pelo PC
        int reg1_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança PC para ler o próximo parâmetro
        registradores.incrementarPC();

        // pega o ID do segundo registrador (Reg 2) apontado pelo PC
        int reg2_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança PC para a próxima instrução
        registradores.incrementarPC();

        // busca os valores dentro desses registradores
        int valReg1 = registradores.getRegistrador(reg1_ID).getValor();
        int valReg2 = registradores.getRegistrador(reg2_ID).getValor();

        // realiza a multiplicação
        // lógica padrão: Reg2 = Reg2 * Reg1
        int resultado = valReg2 * valReg1;

        //salva o resultado no segundo registrador
        registradores.getRegistrador(reg2_ID).setValor(resultado);
    }
}