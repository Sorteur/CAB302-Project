package UserInterface;

import DataClasses.Cell;
import DataClasses.Maze;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class DrawGrid extends Canvas implements MouseInputListener {

    Maze currentMaze;
    JPanel panel;
    int scale;
    int X;
    int Y;
    int size;

    public DrawGrid(JPanel panel, int Scale, Maze maze) {
        this.scale = Scale;
        this.X = maze.getLength();
        this.Y = maze.getHeight();
        this.currentMaze = maze;
        ScaleCalc();

        this.addMouseListener(this);
        panel.setLayout(new BorderLayout());
        panel.add(this, BorderLayout.CENTER);
        this.panel = panel;
    }

    public void ScaleCalc(){
        if (scale == 1){
            size =  64;
        } else {
            size =  16;
        }
    }

    private JToggleButton createButton(String name, Cell A, int width, int height, JFrame frame){
        JToggleButton Button = new JToggleButton(name);
        if (Objects.equals(name, "North")){
            if (!A.isNwall()){Button.setSelected(true);}
            frame.add(Button,BorderLayout.NORTH);
        } else if (Objects.equals(name, "South")){
            if (!A.isSwall()){Button.setSelected(true);}
            frame.add(Button,BorderLayout.SOUTH);
        }else if (Objects.equals(name, "East")){
            if (!A.isEwall()){Button.setSelected(true);}
            frame.add(Button,BorderLayout.EAST);
        }else {
            if (!A.isWwall()){Button.setSelected(true);}
            frame.add(Button,BorderLayout.WEST);
        }
        Button.setPreferredSize(new Dimension(width,height));
        return Button;
    }



    public void EditSquare(int x, int y){
        Cell EditedCell = currentMaze.Search(x,y);
        int index = currentMaze.getGrid().indexOf(EditedCell);
        System.out.println(index);
        Cell NCell;
        Cell SCell;
        Cell WCell;
        Cell ECell;
        if (index % Y != 0) {NCell = currentMaze.getGrid().get(index - 1);} else {NCell = null;}
        if (index % Y != Y-1) {SCell = currentMaze.getGrid().get(index + 1);} else {SCell = null;}
        if (index - Y  >= 0){WCell = currentMaze.getGrid().get(index - Y);} else {WCell = null;}
        if (index + Y  < X*Y){ECell = currentMaze.getGrid().get(index + Y);} else {ECell = null;}



        Frame[] allFrames = Frame.getFrames();
        for (Frame fr: allFrames){
            if (!Objects.equals(fr.getName(), allFrames[0].getName())){
                fr.dispose();
            }
        }
        JFrame PopOut = new JFrame();
        PopOut.setSize(500, 500);
        PopOut.setVisible(true);
        PopOut.setLayout(new BorderLayout());

        JToggleButton North = createButton("North",EditedCell,200,125,PopOut);
        North.addActionListener(e -> {
            EditedCell.setNwall(!EditedCell.isNwall());
            if (NCell != null){
                NCell.setSwall(false);
            }
            repaint();
        });
        JToggleButton South = createButton("South",EditedCell,200,125,PopOut);
        South.addActionListener(e -> {
            EditedCell.setSwall(!EditedCell.isSwall());
            if (SCell != null){
                SCell.setNwall(false);
            }
            repaint();
        });
        JToggleButton East = createButton("East",EditedCell,250,200,PopOut);
        East.addActionListener(e -> {
            EditedCell.setEwall(!EditedCell.isEwall());
            if (ECell != null){
                ECell.setWwall(false);
            }
            repaint();
        });
        JToggleButton West = createButton("West",EditedCell,250,200,PopOut);
        West.addActionListener(e -> {
            EditedCell.setWwall(!EditedCell.isWwall());
            if (WCell != null){
                WCell.setEwall(false);
            }
            repaint();
        });
    }


    public void DrawSquare(Cell cell,Graphics g, int x, int y, int length){

        cell.setPosX(x);
        cell.setPosY(y);

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
        int CurrentCell=0;
        int RelativeX = 0;
        while (RelativeX < X){
            int RelativeY=0;
            while (RelativeY < Y){
                DrawSquare(currentMaze.getGrid().get(CurrentCell),g,PosX+(RelativeX*size),PosY+(RelativeY*size),size);
                RelativeY+=1;
                CurrentCell+=1;
            }
            RelativeX+=1;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int clickX = e.getX();
        int clickY = e.getY();

        for (Cell cell:currentMaze.getGrid()) {
            if (((clickX > cell.getPosX()) && (clickX < cell.getPosX()+size)) && ((clickY > cell.getPosY()) && (clickY < cell.getPosY()+size))){
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