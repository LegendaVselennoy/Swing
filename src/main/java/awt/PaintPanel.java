package awt;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.util.Random;

public class PaintPanel extends JPanel {
    Insets ins; // хранит размеры границы панели
    Random rand;

    PaintPanel(){
        // Разместить рамку вокруг панели
        setBorder(BorderFactory.createLineBorder(Color.RED,5));
        rand=new Random();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Всегда первым вызывать метод суперкласса
        super.paintComponent(g);
        int x,y,x2,y2;

        // Получить высоту и ширину компонента
        int height=getHeight();
        int width=getWidth();

        // Получить размеры границы
        ins=getInsets();

        for (int i = 0; i < 10; i++) {
            x=rand.nextInt(width-ins.left);
            y=rand.nextInt(height-ins.bottom);
            x2=rand.nextInt(width-ins.left);
            y2=rand.nextInt(height-ins.bottom);

            // Нарисовать линию
            g.drawLine(x,y,x2,y2);
        }
    }
}

class PaintDemo{
//    JLabel jLa;
    PaintPanel pp;

    PaintDemo(){
        JFrame jfrm=new JFrame("Paint Demo");
        jfrm.setSize(200,150);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pp=new PaintPanel();
        jfrm.add(pp);

        jfrm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PaintDemo();
            }
        });
    }
}
