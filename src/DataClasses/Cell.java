package DataClasses;

public class Cell {
    public Cell(int id, int posX, int posY, boolean nwall, boolean swall, boolean wwall, boolean ewall, boolean start, boolean finish) {
        Id = id;
        GridX = posX;
        GridY = posY;
        Nwall = nwall;
        Swall = swall;
        Wwall = wwall;
        Ewall = ewall;
        Start = start;
        Finish = finish;
        Vistited = false;
    }

    public int Id;

    int GridX;
    int GridY;

    private int PosX;
    private int PosY;

    private boolean Nwall;
    private boolean Swall;
    private boolean Wwall;
    private boolean Ewall;

    private boolean Start;
    private boolean Finish;

    private boolean Vistited;

    public boolean isVistited() {
        return Vistited;
    }

    public int getPosX() {
        return PosX;
    }

    public int getPosY() {
        return PosY;
    }

    public void setPosX(int x) {
        this.PosX = x;
    }

    public void setPosY(int y) {
        this.PosY = y;
    }

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



}
