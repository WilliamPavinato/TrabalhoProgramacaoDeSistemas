package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class LDS extends Instruction {

    public LDS() {
        //passando os 4 argumentos que a classe instruction pede
        super("LDS", (byte)0x6C, "3/4", 3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        // O Executor já fez o incremento do PC antes de chamar o executar.
        // Então o PC atual aponta para o OPERANDO (o endereço).
        int pc = registradores.getValorPC();

        //Usando 'getWord' (ou getByte) porque 'getPosicaoMemoria' não existe na sua classe Memoria
        // Assumindo que o endereço é um número salvo na memória.
        int enderecoMem = memoria.getWord(pc);

        // passando '1' para incrementarPC, pois ele exige um argumento int
        // Avançamos 1 posição porque acabamos de ler o operando.
        registradores.incrementarPC(1);

        // busca o valor real na memória (no endereço que lemos acima)
        int valorMem = memoria.getWord(enderecoMem);

        // salva no registrador S
        registradores.getRegistradorPorNome("S").setValorInt(valorMem);
    }
}