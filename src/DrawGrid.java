import javax.swing.*;
import java.awt.*;

public class DrawGrid extends Canvas {
    public DrawGrid(JPanel panel, int Scale, int x, int y) {
        this.scale = Scale;
        this.X = x;
        this.Y = y;
        panel.add(this, BorderLayout.CENTER);
        this.panel = panel;
    }
    JPanel panel;
    int scale;
    int X;
    int Y;

    public void paint(Graphics g) {
        int i = 0;
        int size;
        if (scale == 1){
            size = 64;
        } else {
            size = 16;
        }





        while (i < X){
            int j=0;
            while (j < Y){
                g.drawRect(((panel.getWidth()-(size*X))/2)+(i*size),((panel.getHeight()-(size*Y))/2)+(j*size),size,size);
                j+=1;
            }
            i+=1;
        }



    }
}
