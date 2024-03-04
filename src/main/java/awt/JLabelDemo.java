package awt;

import javax.swing.*;
import java.awt.*;

public class JLabelDemo {

    public JLabelDemo(){
        JFrame jfrm=new JFrame("JLabelDemo");
        jfrm.setLayout(new FlowLayout());
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setSize(260,210);

        ImageIcon ii=new ImageIcon("src/awt/swing/iconps.png");
        JLabel jl=new JLabel("Hourglass",ii,JLabel.CENTER);
        jfrm.add(jl);
        jfrm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        new JLabelDemo();
                    }
                }
        );
    }
}
