package UserInterface;

import Engine.MazeGenerator;
import Engine.MazeManager;

import javax.swing.*;
import java.awt.*;

public class SaveMazeDialog {

    private static final SaveMazeDialog instance = new SaveMazeDialog();

    public static SaveMazeDialog Instance(){
        return instance;
    }
    SaveMazeDialog() {
    }

    public void GUI(){
        Font Large  = new Font("Larger",Font.PLAIN, 24 );

        JFrame PopOut = new JFrame();
        PopOut.setSize(350, 200);
        PopOut.setVisible(true);
        PopOut.setLocationRelativeTo(null);
        PopOut.setLayout(new GridBagLayout());

        GridBagConstraints c  =  new GridBagConstraints();
        c.weighty = 0.1;
        c.insets = new Insets(5,5,0,5);
        c.anchor = GridBagConstraints.LINE_START;


        JLabel HeightLabel = new JLabel("Name Of Maze");
        HeightLabel.setFont(Large);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        PopOut.add(HeightLabel,c);

        JTextField MazeName = new JTextField();
        MazeName.setFont(Large);
        MazeName.setPreferredSize(new Dimension(150,30));
        MazeName.setText(MazeManager.Instance().GetMaze().GetDescription());
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weighty = 0;
        PopOut.add(MazeName,c);


        JButton Save = new JButton("Save");
        Save.setFont(Large);
        c.anchor = GridBagConstraints.LINE_END;
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridheight = 3;
        c.gridy = 2;
        Save.addActionListener(a -> {
            try {
                String Description = MazeName.getText();
                MazeManager.Instance().GetMaze().SetDescription(Description);
                MazeManager.Instance().SaveMaze();

                PopOut.dispose();
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(PopOut,"Dimensions of maze must be whole numbers.","Input error",JOptionPane.ERROR_MESSAGE);
            }
        });

        PopOut.add(Save,c);

        JButton SaveAs = new JButton("SaveAs");
        SaveAs.setFont(Large);
        c.anchor = GridBagConstraints.LINE_END;
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridwidth = 2;
        c.gridheight = 3;
        c.gridy = 2;
        SaveAs.addActionListener(a -> {
            try {
                String Description = MazeName.getText();
                MazeManager.Instance().GetMaze().SetDescription(Description);
                MazeManager.Instance().GetMaze().SetId(0);
                MazeManager.Instance().SaveMaze();


                PopOut.dispose();
            } catch(NumberFormatException e) {
                JOptionPane.showMessageDialog(PopOut,"Dimensions of maze must be whole numbers.","Input error",JOptionPane.ERROR_MESSAGE);
            }
        });

        PopOut.add(SaveAs,c);
    }
}
