public class Cell {
    public Cell(int posX, int posy, boolean nwall, boolean swall, boolean wwall, boolean ewall, boolean start, boolean finish) {
        GridX = posX;
        GridY = posy;
        Nwall = nwall;
        Swall = swall;
        Wwall = wwall;
        Ewall = ewall;
        Start = start;
        Finish = finish;
    }

    int GridX;
    int GridY;

    int PosX;
    int PosY;

    public int getGridX() {
        return GridX;
    }

    public int getGridY() {
        return GridY;
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

    private boolean Nwall;
    private boolean Swall;
    private boolean Wwall;
    private boolean Ewall;
    private boolean Start;
    private boolean Finish;

}
