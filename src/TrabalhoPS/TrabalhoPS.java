package TrabalhoPS;

import Interface.*;

public class TrabalhoPS {
        // static ExecutorInterface executor;
        static MontadorInterface montador;

        void main() {
            montador = new MontadorInterface();
        }
}
// descomentar quando for rodar o processador de macros - mais pra frente faço um menu que dê pra escolher

//package TrabalhoPS;
//
//import Interface.ProcessadorMacrosInterface;
//
//public class TrabalhoPS {
//    static ProcessadorMacrosInterface processadorMacros;
//
//    public static void main(String[] args) {
//        processadorMacros = new ProcessadorMacrosInterface();
//    }
//}