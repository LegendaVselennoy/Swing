package awt;

import javax.swing.*;

public class SwingDemo {
    SwingDemo(){
        JFrame jfrm=new JFrame("Swing");
        jfrm.setSize(500,100);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel jlab=new JLabel("Swing позволяет строить мощные графические пользовательские интерфейсы");
        jfrm.add(jlab);
        jfrm.setVisible(true);
    }

    public static void main(String[] args) {
        // Создать фрейм в потоке диспетчеризации событий
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SwingDemo();
            }
        });
    }
}
