package Montador;

import java.util.ArrayList;

public class Output {

    public int startingAddress = 0;
    public int endAddress      = 0;
    private int length         = 0;
    public ArrayList<String> machineCode = new ArrayList<>();

    public int get_length() { return this.length; }

    public void set_length() { this.length = startingAddress - endAddress; }

    public void reset(){
        this.startingAddress = 0;
        this.endAddress      = 0;
        this.length          = 0;
        this.machineCode.clear();
    }
}