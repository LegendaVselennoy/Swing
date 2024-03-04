package swing;

import javax.swing.*;
import java.awt.*;

/**
 * Фрейм со строкой меню, при выборе команды File->About из которого появляется диалоговое окно About
 */
public class DialogFrame extends JFrame {
    private static final int DEFAULT_WIDTH=300;
    private static final int DEFAULT_HEIGHT=200;
    private AboutDialog dialog;

    public DialogFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        //Сконструировать меню File
        JMenuBar menuBar=new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu=new JMenu("File");
        menuBar.add(fileMenu);

        //Ввести в меню пункты About и Exit
        //При выборе пункта меню About открывается одноименное диалоговое окно
        JMenuItem aboutItem=new JMenuItem("About");
        aboutItem.addActionListener(event->{
            if (dialog==null) //в первый раз
                dialog=new AboutDialog(DialogFrame.this);
            dialog.setVisible(true); //Показать диалоговое окно
        });
        fileMenu.add(aboutItem);
        //При выборе пункта меню Exit происходит выход из программы
        JMenuItem exitItem=new JMenuItem("Exit");
        exitItem.addActionListener(event->System.exit(0));
        fileMenu.add(exitItem);
    }

    /**
     * Образец модального диалогового окна, в котором выводится сообщение и ожидается до тех пор,
     * пока пользователь не щелкнет на кнопке ОК
     */
    public static class AboutDialog extends JDialog{
        public AboutDialog(JFrame owner){
            super(owner,"About DialogTest",true);
            //Ввести HTML-метку по центру окна
            add(new JLabel("<html><h1><i>Core Java</i></h><hr>By Cat</html>"), BorderLayout.CENTER);
            //При выборе кнопки ОК диалоговое окно закрывается
            JButton ok=new JButton("OK");
            ok.addActionListener(event->setVisible(false));
            //Ввести кнопку ОК в нижней части окна у южной его границы
            JPanel panel=new JPanel();
            panel.add(ok);
            add(panel,BorderLayout.SOUTH);
            pack();
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            DialogFrame frame=new DialogFrame();
            frame.setTitle("Dialog");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

