package awt;

import javax.swing.*;
import java.awt.*;

public class JTabbedPaneDemo {

    public JTabbedPaneDemo(){
        JFrame jFrame=new JFrame("JTabbedPaneDemo");
        jFrame.setLayout(new FlowLayout());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(400,200);

        JTabbedPane jtp=new JTabbedPane();
        jtp.addTab("Города",new CitiesPanel());
        jtp.addTab("Цвета",new ColorsPanel());
        jtp.addTab("Ароматы",new FlavorsPanel());
        jFrame.add(jtp);

        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JTabbedPaneDemo();
            }
        });
    }
}

class CitiesPanel extends JPanel{
    public CitiesPanel(){
        JButton b1=new JButton("New York");
        add(b1);
        JButton b2=new JButton("London");
        add(b2);
        JButton b3=new JButton("Hong Kong");
        add(b3);
        JButton b4=new JButton("Tokyo");
        add(b4);
    }
}

class ColorsPanel extends JPanel{
    public ColorsPanel(){
        JCheckBox cb1=new JCheckBox("Red");
        add(cb1);
        JCheckBox cb2=new JCheckBox("Green");
        add(cb2);
        JCheckBox cb3=new JCheckBox("Blue");
        add(cb3);
    }
}

class FlavorsPanel extends JPanel{
    public FlavorsPanel(){
        JComboBox<String>jcb=new JComboBox<>();
        jcb.addItem("Vanilla");
        jcb.addItem("Chocolate");
        jcb.addItem("Strawberry");
        add(jcb);
    }
}
