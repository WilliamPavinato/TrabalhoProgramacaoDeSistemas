package TrabalhoPS;

import Interface.*;
import java.io.IOException;

public class TrabalhoPS {
        static ExecutorInterface executor;
        static MontadorInterface montador;
        static ProcessadorMacrosInterface processadorMacros;
        static SICXE sicXE;

        void main() throws IOException{
            sicXE = new SICXE();
        }
}