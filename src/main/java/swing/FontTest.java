package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class FontTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            FontFrame frame=new FontFrame();
            frame.setTitle("FontFrame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
    /**
     * Фрейм с компонентом текстового сообщения
     */
    static class  FontFrame extends JFrame{
        public FontFrame(){
            add(new FontComponent());
            pack();
        }
    }
    /**
     * Компонент, отображающий текстовое сообщение, выровненное по центру в прямоугольной рамке
     */
    static class FontComponent extends JComponent{
        public static final int DEFAULT_WIDTH=300;
        public static final int DEFAULT_HEIGHT=200;

        public void paintComponent(Graphics g){
            Graphics2D g2=(Graphics2D) g;
            String message="Hello, World!";
            Font f=new Font("Serif",Font.BOLD,36);
            g2.setFont(f);

            //Определить размеры текстового сообщения
            FontRenderContext context=g2.getFontRenderContext();
            Rectangle2D bounds=f.getStringBounds(message,context);

            //Определить координаты (х, у) верхнего левого угла текста
            double x=(getWidth()-bounds.getWidth())/2;
            double y=(getHeight()-bounds.getHeight())/2;

            //Сложить подъём с координатой у, чтобы достичь базовой линии
            double ascent=-bounds.getY();
            double baseY=y+ascent;

            //Воспроизвести текстовое сообщение
            g2.drawString(message,(int) x,(int) baseY);
            g2.setPaint(Color.LIGHT_GRAY);

            //Нарисовать базовую линию
            g2.draw(new Line2D.Double(x,baseY,x+bounds.getWidth(),baseY));

            //Нарисовать ограничивающий прямоугольник
            Rectangle2D rect=new Rectangle2D.Double(x,y,bounds.getWidth(),bounds.getHeight());
            g2.draw(rect);
        }
        public Dimension getPreferredSize(){
            return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        }
    }
}
