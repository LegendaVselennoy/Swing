package awt;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SimpleKey extends Frame implements KeyListener {
    String msg="";
    String keyState="";

    public SimpleKey(){
        addKeyListener(this);
        addWindowListener(new MyWindowAdapter());
    }

    @Override
    public void keyTyped(KeyEvent e) {
         msg+=e.getKeyChar();
         repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyState="клавиша нажата";
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyState="клавиша отпущена";
        repaint();
    }

    public void paint(Graphics g){
        g.drawString(msg,20,100);
        g.drawString(keyState,20,50);
    }

    public static void main(String[] args) {
        SimpleKey appWin=new SimpleKey();

        appWin.setSize(new Dimension(200,150));
        appWin.setTitle("SimpleKey");
        appWin.setVisible(true);
    }

    class MyWindowAdapter extends WindowAdapter{
        public void windowClosing(WindowEvent we){
            System.exit(0);
        }
    }
}
