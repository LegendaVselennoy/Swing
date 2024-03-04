package awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JComboBoxDemo {
    String[] timepieces={"IconPs","JavaIcon","LineIcon","JSIcon"};

    public JComboBoxDemo(){
        JFrame jfrm=new JFrame("JComboBoxDemo");
        jfrm.setLayout(new FlowLayout());
        jfrm.setSize(400,250);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создать комбинированный список и добавить его в панель содержимого
        JComboBox<String> jcb=new JComboBox<>(timepieces);
        jfrm.add(jcb);

        JLabel jLab=new JLabel(new ImageIcon("src/awt/swing/iconps.png"));
        jfrm.add(jLab);

        jcb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s=(String) jcb.getSelectedItem();
                jLab.setIcon(new ImageIcon("src/awt/swing/"+s+".png"));
            }
        });

        jfrm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JComboBoxDemo::new);
    }
}
