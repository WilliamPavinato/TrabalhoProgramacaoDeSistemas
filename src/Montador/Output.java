package Montador;

import java.util.ArrayList;

public class Output {

    public int startingAddress = 0;
    public int endAddress = 0;
    private int length = 0;
    public ArrayList<String> machineCode = new ArrayList<String>();

    public int getLength()
    {
        return this.length;
    }

    public void setLength()
    {
        this.length = startingAddress - endAddress;
    }

    public void reset(){
        this.startingAddress = 0;
        this.endAddress = 0;
        this.length = 0;
        this.machineCode.clear();
    }
}