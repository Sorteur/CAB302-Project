package UserInterface;

import Engine.MazeGenerator;
import Engine.MazeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class LoadMazeDialog {

    private static final LoadMazeDialog instance = new LoadMazeDialog();

    public static LoadMazeDialog Instance(){
        return instance;
    }
    LoadMazeDialog() {

    }

    public void GUI(){
        Font Large  = new Font("Larger",Font.PLAIN, 24 );

        JFrame PopOut = new JFrame();
        PopOut.setSize(400, 200);
        PopOut.setVisible(true);
        PopOut.setLocationRelativeTo(null);
        PopOut.setLayout(new GridBagLayout());

        GridBagConstraints c  =  new GridBagConstraints();
        c.weighty = 0.1;
        c.insets = new Insets(5,5,0,5);
        c.anchor = GridBagConstraints.LINE_START;

        JLabel WidthLabel = new JLabel("Select a Maze!");
        WidthLabel.setFont(Large);
        c.gridx = 0;
        c.gridy = 0;

        PopOut.add(WidthLabel,c);

        String[] columnNames = {"Id", "Name"};
        String[][] data = MazeManager.Instance().LoadMazeDescriptions();
        //String[][] data = { {"1","MazeTest1"}, {"2", "MazeTest2"}, {"3", "MazeTest3"}, {"4", "MazeTest4"}, {"5", "MazeTest5"}, {"6", "MazeTest6"}};
        JTable Descriptions = new JTable(data, columnNames);
        Descriptions.setBounds(30, 40, 200, 300);

        JScrollPane scrollPane = new JScrollPane(Descriptions);

        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;

        c.gridy = 1;

        PopOut.add(scrollPane, c);



        JButton Confirm = new JButton("Confirm");
        Confirm.setFont(Large);
        c.anchor = GridBagConstraints.LINE_END;
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;

        c.gridy = 1;
        Confirm.addActionListener(a -> {

        });
        PopOut.add(Confirm,c);
    }
}
