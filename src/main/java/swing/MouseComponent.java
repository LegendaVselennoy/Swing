package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Компонент для операций с мышью по добавлению и удалению квадратов
 */
public class MouseComponent extends JComponent {

    private static final int DEFAULT_WIDTH=300;
    private static final int DEFAULT_HEIGHT=200;

    private static final int SIDE_LENGTH=10;
    private final ArrayList<Rectangle2D> squares;
    private Rectangle2D current;//Квадрат, содержащий курсор мыши

    public MouseComponent(){
        squares=new ArrayList<>();
        current=null;
        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }

    public Dimension getPreferredSize(){
        return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }

    public void paintComponent(Graphics g){
        Graphics2D var=(Graphics2D) g;
        //Нарисовать все квадраты
        for (Rectangle2D r : squares) {
             var.draw(r);
        }
    }

    /**
     * Обнаруживает первый квадрат, содержащий заданную точку
     * @param p a Точка
     * @return the Первый квадрат, содержащий точку р
     */
    public Rectangle2D find(Point2D p){
        for (Rectangle2D r : squares) {
            if (r.contains(p)) return r;
        }
        return null;
    }
    /**
     * Вводит квадрат в коллекцию
     * @param p Центр квадрата
     */

    public void  add(Point2D p){
        double x=p.getX();
        double y=p.getY();

        current=new Rectangle2D.Double(
                x-SIDE_LENGTH/2,
                y-SIDE_LENGTH/2,
                SIDE_LENGTH,SIDE_LENGTH
        );
        squares.add(current);
        repaint();
    }
    /**
     * Удаляет квадрат из коллекции
     * @param s Удаляемый квадрат
     */

    public void remove(Rectangle2D s){
        if (s==null) return;
        if (s==current) current=null;
        squares.remove(s);
        repaint();
    }
    private class MouseHandler extends MouseAdapter{

        public void mousePressed(MouseEvent event){
            //Добавить новый квадрат, если курсор находится за пределами квадрата
            current=find(event.getPoint());
            if (current==null) add(event.getPoint());
        }

        public void mouseClicked(MouseEvent event){
            //Удалить текущий квадрат, если на нём произведён двойной щелчок
            current=find(event.getPoint());
            if (current!=null && event.getClickCount()>=2)
                remove(current);
        }
    }

    private class MouseMotionHandler implements MouseMotionListener{

        public void mouseMoved(MouseEvent event){
            //Задать курсор в виде перекрестья, если он находится внутри квадрата
            if (find(event.getPoint())==null)
                setCursor(Cursor.getDefaultCursor());
            else setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }

        public void mouseDragged(MouseEvent event){
            if (current!=null){
                int x=event.getX();
                int y=event.getY();
                //Перетащить текущий квадрат, чтобы отцентровать его в точке с координатами(x,y)
                current.setFrame(x-SIDE_LENGTH/2,y-SIDE_LENGTH/2,SIDE_LENGTH,SIDE_LENGTH);
                repaint();
            }
        }
    }
}


