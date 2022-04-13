import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DrawGrid extends Canvas implements MouseInputListener {
    public DrawGrid(JPanel panel, int Scale, Maze m) {
        this.scale = Scale;
        this.X = m.Length;
        this.Y = m.Height;
        this.currentMaze = m;
        ScaleCalc();

        this.addMouseListener(this);
        panel.setLayout(new BorderLayout());
        panel.add(this, BorderLayout.CENTER);
        this.panel = panel;
    }
    Maze currentMaze;
    JPanel panel;
    int scale;
    int X;
    int Y;
    int size;


    public void ScaleCalc(){
        if (scale == 1){
            size =  64;
        } else {
            size =  16;
        }
    }

    public void EditSquare(int x, int y){
        Cell A = currentMaze.Search(x,y);

        JFrame Popout = new JFrame();
        Popout.setSize(500, 500);
        Popout.setVisible(true);
        Popout.setLayout(new BorderLayout());


    }



    public void DrawSquare(Cell cell,Graphics g, int x, int y, int length){
        cell.PosX = x;
        cell.PosY = y;

        //North
        if (cell.isNwall()){
        g.drawLine(x,y+length,x+length,y+length);}
        //South
        if (cell.isSwall()){
        g.drawLine(x,y,x+length,y);}
        //East
        if (cell.isEwall()){
        g.drawLine(x+length, y+length,x+length,y);}
        //West
        if (cell.isWwall()){
        g.drawLine(x,y+length,x,y);}

    }


    public void paint(Graphics g) {
        int i = 0;
        while (i < X){
            int j=0;
            while (j < Y){
                DrawSquare(currentMaze.Search(i,j),g,((panel.getWidth()-(size*X))/2)+(i*size),((panel.getHeight()-(size*Y))/2)+(j*size),size);
                j+=1;
            }
            i+=1;

        }


    }


    public void clearGrid(Graphics g){
        currentMaze.Editable = false;
        g.clearRect(((panel.getWidth()-(size*X)-4)/2),((panel.getHeight()-(size*Y)-4)/2),(size*X)+4,(size*Y)+4);
       // panel.remove();
    }







    @Override
    public void mouseClicked(MouseEvent e) {
        int clickX = e.getX();
        int clickY = e.getY();
        for (Cell cell:currentMaze.Grid) {
            if (((clickX > cell.PosX) && (clickX < cell.PosX+size)) && ((clickY > cell.PosY) && (clickY < cell.PosY+size))){
                EditSquare(cell.getGridX(),cell.getGridY());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}