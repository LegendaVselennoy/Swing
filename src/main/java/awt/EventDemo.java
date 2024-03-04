package awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventDemo {
    JLabel jLab;

    EventDemo(){
        JFrame jfrm=new JFrame("Evan Example");
        jfrm.setLayout(new FlowLayout());
        jfrm.setSize(220,90);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton jbtnAlpha=new JButton("Alpha");
        JButton jbtnBeta=new JButton("Beta");

        // Добавить прослушиватель событий действий для кнопки
        jbtnAlpha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLab.setText("Нажата кнопка Альфа");
            }
        });

        jbtnBeta.addActionListener(e -> jLab.setText("Нажата кнопка Beta"));

        jfrm.add(jbtnAlpha);
        jfrm.add(jbtnBeta);

        jLab=new JLabel("Нажмите кнопку");
        jfrm.add(jLab);
        jfrm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EventDemo();
            }
        });
    }
}
