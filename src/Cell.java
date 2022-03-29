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

    boolean Nwall;
    boolean Swall;
    boolean Wwall;
    boolean Ewall;

    boolean Start;
    boolean Finish;

}
