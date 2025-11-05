package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class RSUB extends Instruction {

    public RSUB() {
        super("RSUB", "4C"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // Pega o valor do registrador "L" (Linkage), que guarda o endereço de retorno
        int registerL_Value = registradores.getRegistradorPorNome("L").getValor();

        // Atualiza o PC (Contador de Programa) com o valor de "L"
        // Isso faz o programa "pular" de volta para onde a sub-rotina foi chamada
        registradores.getRegistradorPorNome("PC").setValor(registerL_Value);

        // Incrementa o contador de programa
        registradores.incrementarPC();
    }
}