package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class TIX extends Instruction {

    public TIX() {
        super("TIX", "2C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        //pega o endereço de memória (m) que está logo após o opcode
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        //avança o PC para a próxima instrução
        registradores.incrementarPC();

        //busca o valor armazenado na memória (valor de m)
        int memoryValue = Integer.parseInt(memoria.getPosicaoMemoria(memoryAddress), 16);

        //incrementa o registrador X em 1
        int registerX_Value = registradores.getRegistradorPorNome("X").getValor() + 1;

        //salva o novo valor de volta em X
        registradores.getRegistradorPorNome("X").setValor(registerX_Value);

        // compara o novo valor de X com o valor da memóri, define SW para ser usado pelos Jumps (JEQ, JLT, JGT)
        if (registerX_Value == memoryValue) {
            // igual (=)
            registradores.getRegistradorPorNome("SW").setValor(0);
        } else if (registerX_Value < memoryValue) {
            // menor (<) -> define como -1 para funcionar com JLT
            registradores.getRegistradorPorNome("SW").setValor(-1);
        } else {
            // maior (>) -> define como 1 para funcionar com JGT
            registradores.getRegistradorPorNome("SW").setValor(1);
        }
    }
}