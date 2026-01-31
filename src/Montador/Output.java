package Montador;

import java.util.ArrayList;

public class Output {

    public int startingAddress = 0;
    public int endAddress = 0;
    private int length = 0;
    public ArrayList<String> machineCode = new ArrayList<String>();

    public void set_length() {
        this.length = endAddress - startingAddress;
    }

    public int getLength() {
        return this.length;
    }

    public void reset(){
        this.startingAddress = 0;
        this.endAddress = 0;
        this.length = 0;
        this.machineCode.clear();
    }

    public String getMachineCodeAsString() {
        StringBuilder codeBuilder = new StringBuilder();

        for (String code : machineCode) {
            codeBuilder.append(code);
            codeBuilder.append("\n");
        }

        return codeBuilder.toString();
    }
}