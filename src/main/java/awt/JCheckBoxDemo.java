package awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class JCheckBoxDemo implements ItemListener {

    JLabel label;

    public JCheckBoxDemo(){
        JFrame jFrame=new JFrame("JCheckBoxDemo");
        jFrame.setLayout(new FlowLayout());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(250,100);

        JCheckBox cb=new JCheckBox("C");
        cb.addItemListener(this);
        jFrame.add(cb);

        cb=new JCheckBox("C++");
        cb.addItemListener(this);
        jFrame.add(cb);

        cb=new JCheckBox("Java");
        cb.addItemListener(this);
        jFrame.add(cb);

        cb=new JCheckBox("Perl");
        cb.addItemListener(this);
        jFrame.add(cb);

        label=new JLabel("Выбранные языки");
        jFrame.add(label);

        jFrame.setVisible(true);
    }

    public void itemStateChanged(ItemEvent ie){
        JCheckBox cb=(JCheckBox) ie.getItem();

        if (cb.isSelected()){
            label.setText(cb.getText()+" выбран");
        }else {
            label.setText(cb.getText()+" снят");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JCheckBoxDemo();
            }
        });
    }
}
