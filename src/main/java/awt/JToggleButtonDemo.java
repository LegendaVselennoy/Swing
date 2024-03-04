package awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class JToggleButtonDemo {

    public JToggleButtonDemo(){
        JFrame jFrame=new JFrame("JToggleButtonDemo");
        jFrame.setLayout(new FlowLayout());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(200,100);

        JLabel label=new JLabel("Button is off");

        JToggleButton jToggleButton=new JToggleButton("ON/OFF");

        jToggleButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jToggleButton.isSelected()){
                    label.setText("Button is on");
                }else{
                    label.setText("Button is off");
                }
            }
        });

        jFrame.add(jToggleButton);
        jFrame.add(label);

        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JToggleButtonDemo();
            }
        });
    }
}
