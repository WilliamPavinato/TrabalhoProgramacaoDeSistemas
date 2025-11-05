package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class TIXR extends Instruction {

    public TIXR() {
        super("TIXR", "B8"); // Nome e Opcode
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // TIXR: Incrementa X em 1, e então compara X com outro registrador (r1)

        // Pega o valor atual de X e incrementa 1
        int registerX_Value = (registradores.getRegistradorPorNome("X").getValor()) + 1;

        // Armazena o novo valor (X+1) de volta no registrador X
        registradores.getRegistradorPorNome("X").setValor(registerX_Value);

        // Pega o ID do registrador (r1) (parâmetro 1) do PC (para comparar)
        int registerA_ID = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // Lê e avança PC (para pular o parâmetro 'registerA_ID')
        registradores.incrementarPC();

        // Pega o valor armazenado no registrador (r1)
        int registerA_Value = registradores.getRegistrador(registerA_ID).getValor();

        // Compara o NOVO valor de X com o valor do registrador (r1)
        // e define o registrador de Status (SW)
        if (registerX_Value == registerA_Value) {
            // SW = 0 (Equal)
            registradores.getRegistradorPorNome("SW").setValor(0);
        } else if (registerX_Value > registerA_Value) {
            // SW = 1 (Greater than)
            registradores.getRegistradorPorNome("SW").setValor(1);
        } else {
            // SW = -1 (Less than)
            registradores.getRegistradorPorNome("SW").setValor(-1);
        }
    }
}