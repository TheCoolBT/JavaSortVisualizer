import javax.swing.*;
import java.awt.*;

public class SortFrame extends JFrame{

    public Visualizer visualizer = new Visualizer();


    SortFrame() {
        JButton insert = new JButton();
        insert.setBounds(1200,100,10000,1000);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(1280,720));
        this.getContentPane().add(visualizer);
        this.validate();
        this.pack();
        this.setVisible(true);

        this.add(insert);

    }
}
