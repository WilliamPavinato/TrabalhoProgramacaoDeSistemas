package Executor;

import Instrucoes.Instructions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Executor {

    private int output;
    private boolean stop;
    private final Memoria memoria;
    private final Registradores registradores;
    private final Instructions instructions;

    public Executor(){
        this.registradores = new Registradores();
        this.instructions = new Instructions();
        this.memoria = new Memoria();
        this.output = -1;
    }

    public void setPrograma(String path) throws IOException {

        memoria.limparMemoria();
        registradores.cleanRegistradores();

        File file = new File(path);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            int pos = 0;

            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    memoria.setPosicaoMemoria(pos++, word);
                }
            }
        } catch (IOException exc) {
            System.err.println("Erro de leitura de arquivo: " + exc.getMessage());
            throw exc;
        }
    }

    public void executarPrograma() {

        int pc = registradores.getValorPC();
        String opcode = memoria.getPosicaoMemoria(pc);
        stop = false;

        while (!"00".equals(opcode)) {

            if ("D8".equals(opcode)) { // STOP
                stop = true;
                return;
            }

            registradores.incrementarPC();

            if ("DC".equals(opcode)) { // OUTPUT / WRITE
                setOutput(registradores.getRegistradorPorNome("A").getValor());
           } else {
                instructions.getInstrucao(opcode).executar(memoria, registradores);
            }

            pc = registradores.getValorPC();
            opcode = memoria.getPosicaoMemoria(pc);
        }
    }

    public boolean executarPasso() {

        int pc = registradores.getValorPC();
        String opcode = memoria.getPosicaoMemoria(pc);
        stop = false;

        if ("00".equals(opcode)) return false;

        if ("D8".equals(opcode)) {
            stop = true;
            return true;
        }

        registradores.incrementarPC();

        if ("DC".equals(opcode)) {
            setOutput(registradores.getRegistradorPorNome("A").getValor());
        } else {
            instructions.getInstrucao(opcode).executar(memoria, registradores);
        }

        pc = registradores.getValorPC();
        opcode = memoria.getPosicaoMemoria(pc);

        return !"00".equals(opcode);
    }

    // Getters
    public Registradores getRegistradores() { return registradores; }
    public Memoria getMemoria(){ return memoria; }
    public Instructions getInstrucoes(){ return instructions; }
    public int getOutput(){ return output; }
    public boolean getStop(){ return stop; }

    // Setters
    public void setOutput(int output){ this.output = output; }
}
