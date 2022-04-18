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

        Frame[] allFrames = Frame.getFrames();
        for (Frame fr: allFrames){
            if (fr.getName() != allFrames[0].getName()){
                fr.dispose();
            }
        }
        JFrame Popout = new JFrame();
        Popout.setSize(500, 500);
        Popout.setVisible(true);
        Popout.setLayout(new BorderLayout());
        //Popout.dispose();

        JToggleButton North = new JToggleButton("North");
        if (!A.isNwall()){North.setSelected(true);}
        Popout.add(North,BorderLayout.NORTH);
        North.setPreferredSize(new Dimension(200,125));
        North.addActionListener(e -> {
            A.setNwall(!A.isNwall());
            repaint();
        });


        JToggleButton South = new JToggleButton("South");
        if (!A.isSwall()){South.setSelected(true);}
        Popout.add(South,BorderLayout.SOUTH);
        South.setPreferredSize(new Dimension(200,125));
        South.addActionListener(e -> {
            A.setSwall(!A.isSwall());
            repaint();
        });

        JToggleButton East = new JToggleButton("East");
        if (!A.isEwall()){East.setSelected(true);}
        Popout.add(East,BorderLayout.EAST);
        East.setPreferredSize(new Dimension(250,200));
        East.addActionListener(e -> {
            A.setEwall(!A.isEwall());
            repaint();
        });

        JToggleButton West = new JToggleButton("West");
        if (!A.isWwall()){West.setSelected(true);}
        Popout.add(West,BorderLayout.WEST);
        West.setPreferredSize(new Dimension(250,200));
        West.addActionListener(e -> {
            A.setWwall(!A.isWwall());
            repaint();
        });
    }


    public void DrawSquare(Cell cell,Graphics g, int x, int y, int length){
        cell.PosX = x;
        cell.PosY = y;

        //North
        if (cell.isNwall()){
        g.drawLine(x,y,x+length,y);}
        //South
        if (cell.isSwall()){
        g.drawLine(x,y+length,x+length,y+length);}
        //East
        if (cell.isEwall()){
        g.drawLine(x+length, y+length,x+length,y);}
        //West
        if (cell.isWwall()){
        g.drawLine(x,y+length,x,y);}
    }

    public void paint(Graphics g) {
        int PosX = (panel.getWidth()-(size*X))/2;
        int PosY = (panel.getHeight()-(size*Y))/2;
        int z=0;
        int i = 0;
        while (i < X){
            int j=0;
            while (j < Y){
                DrawSquare(currentMaze.Grid.get(z),g,PosX+(i*size),PosY+(j*size),size);
                j+=1;
                z+=1;
            }
            i+=1;
        }
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
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}