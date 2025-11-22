package TrabalhoPS;

import Interface.ExecutorInterface;
import Montador.Montador;

    public class TrabalhoPS {
        static ExecutorInterface executor;

        void main() {
            Montador montador = new Montador();
            executor = new ExecutorInterface();
        }
}