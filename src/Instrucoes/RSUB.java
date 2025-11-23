package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class RSUB extends Instruction {

    public RSUB() {
        super("RSUB", "4C"); // Opcode 4C
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // pega o valor do registrador "L"
        // oregistrador L contém o endereço de retorno salvo pelo JSUB anterior
        int registerL_Value = registradores.getRegistradorPorNome("L").getValor();

        // atualiza o Contador de Programa (PC) com o valor de L
        // isso faz o programa "pular" de volta para a instrução seguinte à chamada da função
        registradores.getRegistradorPorNome("PC").setValor(registerL_Value);

        // não chamamos registradores.incrementarPC() aqui, o endereço guardado em L já é o local exato
        // se incrementarmos, pularemos uma instrução
    }
}