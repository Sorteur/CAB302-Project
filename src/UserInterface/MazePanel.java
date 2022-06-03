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

public class MazePanel extends JPanel implements MouseListener {
    public MazePanel() {
        this.addMouseListener(this);
        //System.out.println("Hi");
    }

    Maze CurrentMaze;
    int X;
    int Y;
    int sizeScale;
    int PosX;
    int PosY;

    public void GridSet(){
        CurrentMaze = MazeManager.Instance().GetMaze();
        X = MazeManager.Instance().GetMaze().getLength();
        Y = MazeManager.Instance().GetMaze().getHeight();
        sizeScale = (this.getHeight()-48)/Y;
        PosX = (this.getWidth()-(sizeScale *X))/2;
        PosY = (this.getHeight()-(sizeScale *Y))/2;
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
        Cell EditedCell = CurrentMaze.Search(x,y);
        int index = CurrentMaze.getGrid().indexOf(EditedCell);
        final Cell NCell = CurrentMaze.checkNorthCell(index);
        final Cell SCell = CurrentMaze.checkSouthCell(index);
        final Cell WCell = CurrentMaze.checkWestCell(index);
        final Cell ECell = CurrentMaze.checkEastCell(index);

        for (Frame fr: Frame.getFrames()){
            if (!Objects.equals(fr.getName(), Frame.getFrames()[0].getName())){
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
            if (NCell != null) NCell.setSwall(!NCell.isSwall());
            repaint();
        });
        JToggleButton South = createButton("South",EditedCell,200,125,PopOut);
        South.addActionListener(e -> {
            EditedCell.setSwall(!EditedCell.isSwall());
            if (SCell != null) SCell.setNwall(!SCell.isNwall());
            repaint();
        });
        JToggleButton East = createButton("East",EditedCell,250,200,PopOut);
        East.addActionListener(e -> {
            EditedCell.setEwall(!EditedCell.isEwall());
            if (ECell != null) ECell.setWwall(!ECell.isWwall());
            repaint();
        });
        JToggleButton West = createButton("West",EditedCell,250,200,PopOut);
        West.addActionListener(e -> {
            EditedCell.setWwall(!EditedCell.isWwall());
            if (WCell != null) WCell.setEwall(!WCell.isEwall());
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

    public void DrawTriangle(Graphics g,int x, int y){
        Polygon Triangle = new Polygon();
        Triangle.addPoint(x+(sizeScale /3),y);
        Triangle.addPoint(x+(2*(sizeScale /3)),y);
        Triangle.addPoint(x+(sizeScale /2),y+ sizeScale /5);
        g.fillPolygon(Triangle);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int CurrentCell=0;
        int RelativeX = 0;
        while (RelativeX < X){
            int RelativeY=0;
            while (RelativeY < Y){
                Cell cell = CurrentMaze.getGrid().get(CurrentCell);
                if(true){
                    if (CurrentCell == 0){
                        cell.setNwall(false);
                        DrawTriangle(g,PosX,PosY);
                    }
                    if (CurrentCell == (X*Y)-1){
                        cell.setSwall(false);
                        DrawTriangle(g,PosX+((X-1)* sizeScale),PosY+(Y* sizeScale));
                    }
                }
                DrawSquare(cell,g,PosX+(RelativeX* sizeScale),PosY+(RelativeY* sizeScale), sizeScale);
                RelativeY+=1;
                CurrentCell+=1;
            }
            RelativeX+=1;
        }
    }

    public void Export(){
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("C:\\Users\\Tyler\\Documents\\Maze.png"));
        if (chooser.showSaveDialog(this.getParent()) == JFileChooser.APPROVE_OPTION) {
            if (chooser.getSelectedFile().exists()){
                if (JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                    return;}}
            File file = (chooser.getSelectedFile());
            String path = file.getPath();
            BufferedImage MazeImg = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_RGB);
            BufferedImage Cropped = MazeImg.getSubimage(PosX,PosY, sizeScale *X+1, sizeScale *Y+1);
            Graphics2D g = MazeImg.createGraphics();
            this.paintAll(g);
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
        System.out.println("YES");
        for (Cell cell:CurrentMaze.getGrid()) {
            if (((clickX > cell.getPosX()) && (clickX < cell.getPosX()+ sizeScale)) && ((clickY > cell.getPosY()) && (clickY < cell.getPosY()+ sizeScale))){
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