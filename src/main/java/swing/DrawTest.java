package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class DrawTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            DrawFrame frame=new DrawFrame();
            frame.setTitle("DrawTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
    /**
     * Фрейм, содержащий панель с нарисованными двухмерными формами
     */
    static class DrawFrame extends JFrame {
         public DrawFrame(){
             add(new DrawComponent());
             pack();
         }
    }

    /**
     * Компонент, отображающий прямоугольники и эллипсы
     */
    static class DrawComponent extends JComponent{
        public static final int DEFAULT_WIDTH=400;
        public static final int DEFAULT_HEIGHT=400;

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            //Нарисовать прямоугольник
            double leftX = 100;
            double topY = 100;
            double width = 200;
            double height = 150;
            Rectangle2D rect = new Rectangle2D.Double(leftX,topY,width,height);
            g2.draw(rect);

//            g2.setPaint(Color.RED);
//            g2.drawString("Warning!",100,100);

            //сине-зелёный цвет
//            g2.setPaint(new Color(0,128,128));
//            g2.drawString("Welcome",75,125);



            //Нарисовать вписанный эллипс
            Ellipse2D ellipse=new Ellipse2D.Double();
            ellipse.setFrame(rect);
            g2.draw(ellipse);

            //Нарисовать диагональную линию
            g2.draw(new Line2D.Double(leftX,topY,leftX+width,topY+height));

            //Нарисовать окружжность с тем же самым центром
            double centerX=rect.getCenterX();
            double centerY=rect.getCenterY();
            double radius=150;

            Ellipse2D circle=new Ellipse2D.Double();
            circle.setFrameFromCenter(centerX,centerY,centerX+radius,centerY+radius);
            g2.draw(circle);

        }
          public Dimension getPreferredSize(){
            return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
          }
    }
}
