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
        pnlTwo = createPanel(Color.GRAY);
        pnlThree = createPanel(Color.gray);
        pnlFour = createPanel(Color.gray);
        pnlBtn = createPanel(Color.gray);
        getContentPane().add(pnlOne,BorderLayout.CENTER);
        getContentPane().add(pnlTwo,BorderLayout.WEST);
        getContentPane().add(pnlThree,BorderLayout.NORTH);
        getContentPane().add(pnlFour,BorderLayout.EAST);
        getContentPane().add(pnlBtn,BorderLayout.SOUTH);
        //LayoutPanel();
    }

    private JPanel pnlOne;
    private JPanel pnlTwo;
    private JPanel pnlThree;
    private JPanel pnlFour;
    private JPanel pnlBtn;

    private JPanel createPanel(Color c) {
        JPanel A = new JPanel();
        A.setBackground(c);
        return A;
    }

    private void layoutButtonPanel() {
        GridBagLayout layout = new GridBagLayout();
        pnlBtn.setLayout(layout);
        //Lots of layout code here
    }

    private JButton createButton(String str) {
        JButton A = new JButton(str);
//Create a JButton object and store it in a local var
//Set the button text to that passed in str
//Add the frame as an actionListener
//Return the JButton object
        return A;
    }

    @Override
    public void run(){
        createGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}


