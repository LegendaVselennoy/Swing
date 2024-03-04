package awt;

import java.awt.*;
import java.awt.event.*;

public class MouseEventDemo extends Frame implements MouseListener, MouseMotionListener {

    String msg="";
    int mouseX=0,mouseY=0; // координаты указателя мыши

    public MouseEventDemo(){
        addMouseListener(this);
        addMouseMotionListener(this);
        addWindowListener(new MyWindowAdapter());
    }

    public void mouseClicked(MouseEvent me){
        msg=msg+" -- получен щелчок";
        repaint();
    }

    public void mouseEntered(MouseEvent me){
        mouseX=100;
        mouseY=100;
        msg="Указатель мыши наведен на окно";
        repaint();
    }

    public void mouseExited(MouseEvent me){
        mouseX=100;
        mouseY=100;
        msg="Указатель мыши покинул окно";
        repaint();
    }

    public void mousePressed(MouseEvent me){
        mouseX= me.getX();
        mouseY=me.getY();
        msg="Кнопка нажата";
        repaint();
    }

    public void mouseReleased(MouseEvent me){
        mouseX=me.getX();
        mouseY=me.getY();
        msg="Кнопка отпущена";
        repaint();
    }

    public void mouseDragged(MouseEvent me){
        mouseX= me.getX();
        mouseY=me.getY();
        msg="*"+" курсор мыши находится в "+mouseX+", "+mouseY;
        repaint();
    }

    public void mouseMoved(MouseEvent me){
        msg="Перемещение курсора мыши в "+me.getX()+", "+me.getY();
        repaint();
    }

    public void paint(Graphics g){
        g.drawString(msg,mouseX,mouseY);
    }

    public static void main(String[] args) {
        MouseEventDemo appWin=new MouseEventDemo();
        appWin.setSize(new Dimension(300,300));
        appWin.setTitle("MouseEventDemo");
        appWin.setVisible(true);
    }

    class MyWindowAdapter extends WindowAdapter{
        public void windowClosing(WindowEvent we){
            System.exit(0);
        }
    }
}
