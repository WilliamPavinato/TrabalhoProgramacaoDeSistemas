package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class TIXR extends Instruction {

    public TIXR() {
        // Opcode "B8" para TIXR
        super("TIXR", "B8");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // TIXR r1 -> compara X com o registrador r1
        int reg1_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança o PC para a próxima instrução
        registradores.incrementarPC();

        //incrementa o registrador X em 1
        int registerX_Value = registradores.getRegistradorPorNome("X").getValor() + 1;

        //salva o novo valor de volta em X
        registradores.getRegistradorPorNome("X").setValor(registerX_Value);

        //busca o valor do registrador de comparação (r1)
        int registerA_Value = registradores.getRegistrador(reg1_ID).getValor();

        //compara X (incrementado) com r1
        if (registerX_Value == registerA_Value) {
            // igual (=)
            registradores.getRegistradorPorNome("SW").setValor(0);
        } else if (registerX_Value < registerA_Value) {
            // menor (<) -> define como -1 para funcionar com JLT
            registradores.getRegistradorPorNome("SW").setValor(-1);
        } else {
            // maior (>) -> define como 1 para funcionar com JGT
            registradores.getRegistradorPorNome("SW").setValor(1);
        }
    }
}