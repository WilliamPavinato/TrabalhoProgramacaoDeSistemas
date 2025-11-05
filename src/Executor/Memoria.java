package Executor;

import java.util.ArrayList;
import java.util.Collections;

public class Memoria {
    private ArrayList<String> memoria;

    Memoria() {
        memoria = new ArrayList<String>(Collections.nCopies(350, "00")); // faz 350 palavras de memória
    }

    /**
     * Retorna o array da memória
     * @return array contendo toda memória
     */
    public ArrayList<String> getMemoria() {
        return memoria;
    }

    /**
     * Limpa array setando tudo como 0
     */
    public void limparMemoria()
    {
        for (int i = 0; i < memoria.size(); i++)
            this.memoria.set(i, "00");
    }

    /**
     * Retorna palavra armazenada na posicao informada
     * LOAD
     * @param posicao
     */
    public String getPosicaoMemoria(int posicao) {
        return memoria.get(posicao);
    }

    /**
     * Armazena determinada palavra numa posicao na memoria
     * STORE
     * @param posicao
     * @param valor a ser armazenado
     */
    public void setPosicaoMemoria(int posicao, String valor){
        memoria.set(posicao, valor);
    }

}