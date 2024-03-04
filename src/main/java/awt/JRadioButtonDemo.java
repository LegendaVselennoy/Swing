package awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JRadioButtonDemo implements ActionListener {
    JLabel label;

    public JRadioButtonDemo(){
        JFrame jfrm=new JFrame("JRadioButtonDemo");
        jfrm.setLayout(new FlowLayout());
        jfrm.setSize(250,100);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JRadioButton b1 =new JRadioButton("A");
        b1.addActionListener(this);
        jfrm.add(b1);

        JRadioButton b2 =new JRadioButton("B");
        b2.addActionListener(this);
        jfrm.add(b2);

        JRadioButton b3 =new JRadioButton("C");
        b3.addActionListener(this);
        jfrm.add(b3);

        ButtonGroup bg=new ButtonGroup();
        bg.add(b1);
        bg.add(b2);
        bg.add(b3);

        label=new JLabel("Выберите одну");
        jfrm.add(label);

        jfrm.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        label.setText("Выбран "+e.getActionCommand());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JRadioButtonDemo();
            }
        });
    }
}
