import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI2 extends JFrame implements ActionListener, Runnable {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    public Maze CurrentMaze = new Maze(6,4);
    public GUI2(String string) throws HeadlessException {

    }

    private void createGUI() {
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);
        pnlOne = createPanel(Color.white);
        //pnlOne.add(m);
        pnlTwo = createPanel(Color.GRAY);
        pnlThree = createPanel(Color.gray);
        getContentPane().add(pnlOne,BorderLayout.CENTER);
        getContentPane().add(pnlTwo,BorderLayout.WEST);
        getContentPane().add(pnlThree,BorderLayout.NORTH);
        LayoutPanel();
    }

    private JPanel pnlOne;
    private JPanel pnlTwo;
    private JPanel pnlThree;

    private JPanel createPanel(Color c) {
        JPanel A = new JPanel();
        A.setBackground(c);
        return A;
    }
    //DrawGrid m = new DrawGrid(1,CurrentMaze.Length,CurrentMaze.Height);
    private void LayoutPanel(){
        GridBagLayout layout = new GridBagLayout();
        //add components to grid
        GridBagConstraints constraints = new GridBagConstraints();
//Defaults
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 100;
        constraints.weighty = 100;
        //pnlOne.setLayout(layout);
        //pnlTwo.setLayout(layout);
        //pnlThree.setLayout(layout);
        //pnlOne.add(m);
    }

    @Override
    public void run(){
        createGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}


