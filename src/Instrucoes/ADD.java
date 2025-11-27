package Instrucoes;

import Executor.Memoria;
import Executor.Registradores;

public class ADD extends Instruction {

    public ADD() {
        super("ADD", (byte)0x18, "3/4",3);
    }

    @Override
    public void executar(Memoria memoria, Registradores registradores) {
        
        int TA = calcularTA(registradores, memoria);
        
        Map<String, Boolean> flags = getFlags();

        if (flags.get("n") && !flags.get("i"))
        {
            TA = memoria.getWord(memoria.getWord(TA)); 
        }

        else if ((!flags.get("n") && !flags.get("i")) || (flags.get("n") && flags.get("i"))) 
        {
            TA = memoria.getWord(TA);
        }

        
        int valorAcumulator = registradores.getRegistradorPorNome("A").getValor(); // Valor do Acumulador
        
        valorAcumulator = TA + valorAcumulator;

       
        registradores.getRegistradorPorNome("A").setValor(valorAcumulator); // Armazena resultado
    }
    
}