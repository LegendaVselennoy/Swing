package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.prefs.Preferences;

public class ImageViewer {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            ImageViewerFrame frame=new ImageViewerFrame();
            frame.setTitle("ImageViewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    /**
     * Средство просмотра изображений, восстанавливающее положение, размеры и просматриваемое изображение
     * из пользовательских глобальных параметров настройки и обновляющее эти параметры по завершении работы
     */

    static class ImageViewerFrame extends JFrame{

        public static final int DEFAULT_WIDTH=300;
        public static final int DEFAULT_HEIGHT=200;

        public ImageViewerFrame(){
            Preferences root=Preferences.userRoot();
            Preferences node=root.node("/swing/ImageViewer");
            //Получить положение, размеры и заглавие из свойств
            int left=node.getInt("left",0);
            int top=node.getInt("top",0);
            int width=node.getInt("width",DEFAULT_WIDTH);
            int height= node.getInt("height",DEFAULT_HEIGHT);
            setBounds(left,top,width,height);
//            image=node.get("image",null);
            JLabel label=new JLabel();
//            if (image!=null)
//                label.setIcon(new ImageIcon());

            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent event){
                    node.putInt("left",getX());
                    node.putInt("top",getY());
                    node.putInt("width",getWidth());
                    node.putInt("height",getHeight());
//                    node.putInt("image",image);
                }
            });
            //Воспользоваться меткой для воспроизведения изображений
            add(label);

            //Установить селектор файлов
            JFileChooser chooser=new JFileChooser();
            chooser.setCurrentDirectory(new File("."));

            //Установить строку меню
            JMenuBar menuBar=new JMenuBar();
            setJMenuBar(menuBar);

            JMenu menu=new JMenu("File");
            menuBar.add(menu);

            JMenuItem openItem=new JMenuItem("Open");
            menu.add(openItem);
            openItem.addActionListener(event->{
                //Отобразить диалоговое окно селектора файлов
                int result=chooser.showOpenDialog(null);
                //Если файл выбран,установить его в виде пиктограмммы метки
                if (result==JFileChooser.APPROVE_OPTION){
//                    image=chooser.getSelectedFile().getPath();
//                    label.setIcon(new ImageIcon(image));
                }
            });
            JMenuItem exitItem=new JMenuItem("Exit");
            menu.add(exitItem);
            exitItem.addActionListener(event->System.exit(0));
        }
    }
}
