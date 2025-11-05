package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class TIX extends Instruction {

    public TIX() {
        super("TIX", "2C"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // TIX: Incrementa X em 1, e então compara X com o operando (m)

        // Pega o valor atual de X e incrementa 1
        int registerX_Value = (registradores.getRegistradorPorNome("X").getValor()) + 1;

        // Armazena o novo valor (X+1) de volta no registrador X
        registradores.getRegistradorPorNome("X").setValor(registerX_Value);

        // Pega o endereço de memória (parâmetro 1) do PC (o operando 'm')
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Lê e avança PC (para pular o parâmetro 'memoryAddress')
        registradores.incrementarPC();

        // Pega o valor armazenado na posição de memória lida (valor de 'm')
        int memoryValue = Integer.parseInt(memoria.getPosicaoMemoria(memoryAddress), 16);

        // Compara o NOVO valor de X com o valor da memória (m)
        // e define o registrador de Status (SW)
        if (registerX_Value == memoryValue) {
            // SW = 0 (Equal)
            registradores.getRegistradorPorNome("SW").setValor(0);
        } else if (registerX_Value < memoryValue) {
            // SW = -1 (Less than)
            registradores.getRegistradorPorNome("SW").setValor(-1);
        } else {
            // SW = 1 (Greater than)
            registradores.getRegistradorPorNome("SW").setValor(1);
        }
    }
}