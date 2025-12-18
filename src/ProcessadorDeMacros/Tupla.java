package ProcessadorDeMacros;

public class Tupla {
    private int startPointer;
    private int endPointer;

    public Tupla(int startPointer, int endPointer) {
        this.startPointer = startPointer;
        this.endPointer = endPointer;
    }

    public int getStartPointer() {
        return startPointer;
    }

    public int getEndPointer() {
        return endPointer;
    }

    public void setStartPointer(int startPointer) {
        this.startPointer = startPointer;
    }

    public void setEndPointer(int endPointer) {
        this.endPointer = endPointer;
    }
}
