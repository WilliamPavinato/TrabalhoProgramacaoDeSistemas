package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDT extends Instruction {
    public LDT() {
        super("LDT", (byte)0x74, "3/4", 3); // nome, opcode, formato, tamanho
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        //obtém o valor atual do Contador de Programa (PC)
        int pc = registradores.getValorPC();

        //lê o endereço de memória do operando
        int endereco = memoria.getByte(pc) & 0xFF;

        //incrementa o PC em 1
        //isso é necessário para "pular" o byte do endereço que acabamos de ler,ndeixando o PC pronto para a próxima instrução
        registradores.incrementarPC(1);

        //busca o valor real armazenado na memória
        //vai até o 'endereco' que lemos acima e pega a palavra (Word = 3 bytes) armazenada lá
        int valor = memoria.getWord(endereco);

        //salva o valor no Registrador T
        //atualiza o conteúdo do registrador T com o dado trazido da memória.
        registradores.getRegistradorPorNome("T").setValorInt(valor);
    }
}