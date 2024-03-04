package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Фрейм с панелью экранных кнопок
 */

 public class ButtonFrame extends JFrame {
     private final JPanel buttonPanel;
    private static final int DEFAULT_WIDTH=300;
    private static final int DEFAULT_HEIGHT=200;

    public ButtonFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        //Создать экранные кнопки
        JButton yellowButton = new JButton("Yellow");
        JButton blueButton = new JButton("Blue");
        JButton redButton = new JButton("Red");

        buttonPanel=new JPanel();

        //Ввести экранные кнопки на панели
        buttonPanel.add(yellowButton);
        buttonPanel.add(blueButton);
        buttonPanel.add(redButton);

        //Ввести панель во фрейм
        add(buttonPanel);

        //Сформировать действия экранных кнопок
        ColorAction yellowAction=new ColorAction(Color.YELLOW);
        ColorAction blueAction=new ColorAction(Color.BLUE);
        ColorAction redAction=new ColorAction(Color.RED);

        //Связать действия с экранными кнопками
        yellowButton.addActionListener(yellowAction);
        blueButton.addActionListener(blueAction);
        redButton.addActionListener(redAction);
    }
    /**
     * Приёмник действий , устанавливающий цвет фона панели
     */
    private class ColorAction implements ActionListener{
        private final Color backgroundColor;

        public ColorAction(Color c) {
            backgroundColor = c;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            buttonPanel.setBackground(backgroundColor);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            ButtonFrame frame=new ButtonFrame();
            frame.setTitle("Colors");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}
