package Ligador;

import Montador.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ligador {
    private final ArrayList<String> programas; // Lista de programas a serem ligados
    private final Map<String, Integer> tabelaDeSimbolosGlobal; // Tabela de símbolos global

    public Ligador() {
        programas = new ArrayList<>();
        tabelaDeSimbolosGlobal = new HashMap<>();
    }

    public void adicionarPrograma(String programa) {
        programas.add(programa);
    }

    public String ligarProgramas() {
        // Constrói a tabela de símbolos global
        primeiraPassagem();

        // Realiza a ligação dos programas
        return segundaPassagem();
    }

    private void primeiraPassagem() {
        int enderecoAtual = 0;

        for (String programa : programas) {
            Montador montador = new Montador();
            montador.setPrograma(programa);
            montador.passoUm();

            // Adiciona os símbolos do programa à tabela de símbolos global
            tabelaDeSimbolosGlobal.putAll(montador.getSYMTAB());

            // Incrementa o endereço atual considerando o tamanho do programa

            enderecoAtual += montador.geOutput().get_length();
        }
    }

    private String segundaPassagem() {
        StringBuilder codigoLigado = new StringBuilder();
        int enderecoAtual = 0;

        for (String programa : programas) {
            Montador montador = new Montador();
            montador.setPrograma(programa);
            montador.passoUm(); // Executa a primeira passagem do montador
            montador.passoDois(); // Executa a segunda passagem do montador

            // Obtém o código de máquina do programa atual e realiza eventuais ajustes de
            // endereço
            String codigoPrograma = montador.geOutput().getMachineCodeAsString();

            // Incrementa o endereço atual considerando o tamanho do programa
            enderecoAtual += montador.geOutput().get_length();

            // Adiciona o código do programa ligado ao código final, considerando eventuais
            // ajustes de endereço
            codigoLigado.append(ajustarEnderecos(codigoPrograma, enderecoAtual));

        }

        return codigoLigado.toString();
    }

    private String ajustarEnderecos(String codigoPrograma, int enderecoBase) {
        StringBuilder codigoAjustado = new StringBuilder();


        return codigoAjustado.toString();
    }

    public Map<String, Integer> getTabelaDeSimbolosGlobal() {
        return tabelaDeSimbolosGlobal;
    }

    public static void main(String[] args) {
        // Teste ligador
        Ligador ligador = new Ligador();
        ligador.adicionarPrograma("/txtFiles/outputMacro.asm");
        ligador.adicionarPrograma("/txtFiles/outputMacroNested.asm");

        String codigoLigado = ligador.ligarProgramas();
        System.out.println("Código ligado:\n" + codigoLigado);

        // Teste da tabela de simbolos
        Map<String, Integer> tabelaGlobal = ligador.getTabelaDeSimbolosGlobal();
        System.out.println("Tabela de Símbolos Global:\n" + tabelaGlobal);
    }
}