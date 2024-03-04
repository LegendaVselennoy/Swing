package swing;

import javax.swing.*;
import java.awt.*;

public class NotHelloWorld {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            NotHelloWorldFrame frame=new NotHelloWorldFrame();
            frame.setTitle("NotHelloWorld");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    /**
     * Фрейм, содержащий панель сообщений
     */
    static class NotHelloWorldFrame extends JFrame {
        public NotHelloWorldFrame(){
            add(new NotHelloWorldComponent());
            pack();
        }
    }
    /**
     * Компонент, выводящий сообщение
     */
    static class NotHelloWorldComponent extends JComponent{
        public static final int MESSAGE_X=75;
        public static final int MESSAGE_Y=100;
        public static final int DEFAULT_WIDTH=500;
        public static final int DEFAULT_HEIGHT=200;

        public void paintComponent(Graphics g){
            g.drawString("Not a Hello, World program. Ты сможешь,ты лучший!",MESSAGE_X,MESSAGE_Y);
        }
        public Dimension getPreferredSize(){
            return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        }
    }
}
