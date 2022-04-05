import java.awt.*;

public class DrawGrid extends Canvas {
    public DrawGrid(int Scale, int x, int y) {
        this.scale = Scale;
        this.X = x;
        this.Y = y;
    }

    int scale;
    int X;
    int Y;

    public void paint(Graphics g) {
        int i = X;
        int size;
        if (scale == 1){
            size = 64;
        } else {
            size = 16;
        }
        while (i > 0){
            int j=Y;
            while (j > 0){
                g.drawRect(20+(i*size),20+(j*size),size,size);
                j-=1;
            }
            i-=1;
        }

    }

}
