public class Cell {
    public Cell(int posX, int posy, boolean nwall, boolean swall, boolean wwall, boolean ewall, boolean start, boolean finish) {
        PosX = posX;
        Posy = posy;
        Nwall = nwall;
        Swall = swall;
        Wwall = wwall;
        Ewall = ewall;
        Start = start;
        Finish = finish;
    }

    int PosX;
    int Posy;

    public int getPosX() {
        return PosX;
    }

    public int getPosy() {
        return Posy;
    }

    public boolean isNwall() {
        return Nwall;
    }

    public void setNwall(boolean nwall) {
        Nwall = nwall;
    }

    public boolean isSwall() {
        return Swall;
    }

    public void setSwall(boolean swall) {
        Swall = swall;
    }

    public boolean isWwall() {
        return Wwall;
    }

    public void setWwall(boolean wwall) {
        Wwall = wwall;
    }

    public boolean isEwall() {
        return Ewall;
    }

    public void setEwall(boolean ewall) {
        Ewall = ewall;
    }

    public boolean isStart() {
        return Start;
    }

    public void setStart(boolean start) {
        Start = start;
    }

    public boolean isFinish() {
        return Finish;
    }

    public void setFinish(boolean finish) {
        Finish = finish;
    }

    boolean Nwall;
    boolean Swall;
    boolean Wwall;
    boolean Ewall;
    boolean Start;
    boolean Finish;

}
