package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;
//preciso do outputMontador.txt
public class LDS extends Instruction {

    // define o nome e o opcode
    public LDS() {
        // o montador espera que o opcode seja uma String "6C"
        super("LDS", "6C");
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // o montador escreve no arquivo opcode - endereço, quando essa instrução roda, o PC está apontando para o endereço
        // pega o endereço de memória onde está o valor
        int memoryAddress = Integer.parseInt(memoria.getPosicaoMemoria(registradores.getValorPC()), 16);

        // opcional: avança o PC para pular o parâmetro que acabamos de ler
        // Isso depende se o ciclo de busca já incrementou ou não, mas seguindo o padrão dos anteriores:
        registradores.incrementarPC();

        // vai na memória, naquele endereço, e pega o valor real
        int memoryValue = Integer.parseInt(memoria.getPosicaoMemoria(memoryAddress), 16);

        // salva o valor no registrador S
        registradores.getRegistradorPorNome("S").setValor(memoryValue);
    }
}