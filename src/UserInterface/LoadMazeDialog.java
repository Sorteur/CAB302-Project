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

    public void GUI(MazePanel pnlMaze){
        Font Large  = new Font("Larger",Font.PLAIN, 24 );

        JFrame PopOut = new JFrame();
        PopOut.setSize(600, 200);
        PopOut.setVisible(true);
        PopOut.setLocationRelativeTo(null);
        PopOut.setLayout(new GridBagLayout());

        GridBagConstraints c  =  new GridBagConstraints();
        c.weighty = 0.1;
        c.insets = new Insets(10,0,10,5);
        c.anchor = GridBagConstraints.LINE_START;


        JLabel Label = new JLabel("Select a Maze!");
        Label.setFont(Large);
        c.gridx = 0;
        c.gridy = 0;

        PopOut.add(Label,c);


        String[] columnNames = {"Id", "Name", "Author", "Creation Date", "Last Edited"};
        String[][] data = MazeManager.Instance().LoadMazeDescriptions();
        JTable Descriptions = new JTable(data, columnNames);
        //Descriptions.getTableHeader().setReorderingAllowed(false);
        Descriptions.setPreferredScrollableViewportSize(new Dimension(600, 100));
        Descriptions.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(Descriptions);
        scrollPane.setPreferredSize(new Dimension(884, 194));

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
            int id = 0;

            id = Descriptions.getSelectedRow();

            try{
                id = Integer.parseInt(data[id][0]);
            }
            catch (NumberFormatException ex){
                ex.printStackTrace();
            }

            MazeManager.Instance().LoadMazeFromId(id);
            pnlMaze.add(new LogoPlacer());
            pnlMaze.add(new SrtEndPlacer());
            pnlMaze.GridSet();
            pnlMaze.updateUI();


            //MazeManager.Instance().pnlMaze.
            PopOut.dispose();
        });
        PopOut.add(Confirm,c);
    }
}
