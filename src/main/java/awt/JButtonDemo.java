package awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JButtonDemo implements ActionListener {
    JLabel label;

    public JButtonDemo(){
        JFrame jFrame=new JFrame("JButtonDemo");
        jFrame.setLayout(new FlowLayout());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(500,450);

        ImageIcon hourglass=new ImageIcon("src/awt/swing/iconps.png");
        JButton jb=new JButton(hourglass);
        jb.setActionCommand("Hourglass");
        jb.addActionListener(this);
        jFrame.add(jb);

        ImageIcon analog=new ImageIcon("src/awt/swing/iconps.png");
        jb=new JButton(analog);
        jb.setActionCommand("Analog");
        jb.addActionListener(this);
        jFrame.add(jb);

        ImageIcon digital=new ImageIcon("src/awt/swing/iconps.png");
        jb=new JButton(digital);
        jb.setActionCommand("Digital");
        jb.addActionListener(this);
        jFrame.add(jb);

        ImageIcon stopWatch=new ImageIcon("src/awt/swing/iconps.png");
        jb=new JButton(stopWatch);
        jb.setActionCommand("StopWatch");
        jb.addActionListener(this);
        jFrame.add(jb);

        label=new JLabel("Choose");
        jFrame.add(label);
        jFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        label.setText("You "+e.getActionCommand());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JButtonDemo();
            }
        });
    }
}
