package UserInterface;

import DataClasses.Cell;
import DataClasses.Maze;
import Engine.MazeManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DrawGrid extends Canvas implements MouseListener {
    public DrawGrid(JPanel panel) {
        this.panel = panel;
        this.addMouseListener(this);
    }

    Maze CurrentMaze;
    JPanel panel;
    int X;
    int Y;
    int size;
    int PosX;
    int PosY;

    public void GridSet(Maze maze){
        CurrentMaze = maze;
        X = maze.getLength();
        Y = maze.getHeight();
        size = (panel.getHeight()-48)/Y;
        PosX = (panel.getWidth()-(size*X))/2;
        PosY = (panel.getHeight()-(size*Y))/2;
        panel.updateUI();
        repaint();
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
//TODO MOVE TO MAZE CLASS AND SPLIT INTO DIFFERENT CLASSES
    public void EditSquare(int x, int y){
        Cell EditedCell = MazeManager.Instance().GetMaze().Search(x,y);
        int index = MazeManager.Instance().GetMaze().getGrid().indexOf(EditedCell);
        //System.out.println(index);
        Cell NCell;
        Cell SCell;
        Cell WCell;
        Cell ECell;
        if (index % Y != 0) {NCell = MazeManager.Instance().GetMaze().getGrid().get(index - 1);} else {NCell = null;}
        if (index % Y != Y-1) {SCell = MazeManager.Instance().GetMaze().getGrid().get(index + 1);} else {SCell = null;}
        if (index - Y  >= 0){WCell = MazeManager.Instance().GetMaze().getGrid().get(index - Y);} else {WCell = null;}
        if (index + Y < X*Y){ECell = MazeManager.Instance().GetMaze().getGrid().get(index + Y);} else {ECell = null;}



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
        int CurrentCell=0;
        int RelativeX = 0;
        while (RelativeX < X){
            int RelativeY=0;
            while (RelativeY < Y){
                Cell cell = MazeManager.Instance().GetMaze().getGrid().get(CurrentCell);
                if (cell.isStart())
                {
                    g.setColor(Color.GREEN);
                    g.fillRect(PosX+(RelativeX*size),PosY+(RelativeY*size),size,size);
                }
                if (cell.isFinish())
                {
                    g.setColor(Color.RED);
                    g.fillRect(PosX+(RelativeX*size),PosY+(RelativeY*size),size,size);
                }
                g.setColor(Color.BLACK);
                DrawSquare(cell,g,PosX+(RelativeX*size),PosY+(RelativeY*size),size);
                RelativeY+=1;
                CurrentCell+=1;
            }
            RelativeX+=1;
        }
    }

    public void Export(){
        JFileChooser F = new JFileChooser();
        F.setSelectedFile(new File("C:\\Users\\Tyler\\Documents\\Maze.png"));
        if (F.showSaveDialog(panel.getParent()) == JFileChooser.APPROVE_OPTION) {
            File file = (F.getSelectedFile());
            String path = file.getPath();
            BufferedImage MazeImg = new BufferedImage(panel.getWidth(),panel.getHeight(),BufferedImage.TYPE_INT_RGB);
            BufferedImage Cropped = MazeImg.getSubimage(PosX,PosY,size*X+1,size*Y+1);

            Graphics2D g = MazeImg.createGraphics();
            panel.paintAll(g);
            try {
                ImageIO.write(Cropped,"png",new File(path));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int clickX = e.getX();
        int clickY = e.getY();
        //System.out.println("Here!");

        for (Cell cell:CurrentMaze.getGrid()) {
            if (((clickX > cell.getPosX()) && (clickX < cell.getPosX()+size)) && ((clickY > cell.getPosY()) && (clickY < cell.getPosY()+size))){
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
}