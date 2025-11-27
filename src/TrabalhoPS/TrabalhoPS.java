package TrabalhoPS;

import Interface.ExecutorInterface;
import Montador.Montador;

    public class TrabalhoPS {
        static ExecutorInterface executor;

        void main() {
            Montador montador = new Montador();
            montador.montarPrograma(System.getProperty("user.dir")+ "/ArquivosTXT/inputMontador.txt");
            executor = new ExecutorInterface();
        }
}