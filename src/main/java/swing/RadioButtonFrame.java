package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Фрейм с меткой образцового текста и кнопками-переключателями для выбора размера шрифта
 */
public class RadioButtonFrame extends JFrame {
    private JPanel buttonPanel;
    private ButtonGroup group;
    private JLabel label;
    private static final int DEFAULT_SIZE=36;

    public RadioButtonFrame(){
        //Ввести метку с образцовым текстом
        label=new JLabel("The quick brown fox jumps over the lazy dog.");
        label.setFont(new Font("Serif",Font.PLAIN,DEFAULT_SIZE));
        add(label,BorderLayout.CENTER);

        //Ввести кнопки-переключатели

        buttonPanel=new JPanel();
        group=new ButtonGroup();

        addRadioButton("Small",8);
        addRadioButton("Medium",12);
        addRadioButton("Large",18);
        addRadioButton("Extra large",36);

        add(buttonPanel,BorderLayout.SOUTH);
        pack();
    }

    /**
     * Вводит кнопку-переключатель, устанавливающую размер шрифта для выделения образцового текста
     * @param name Строка надписи на кнопке
     * @param size Размер шрифта, устанавливаемый данной кнопкой
     */
    public void addRadioButton(String name,int size){
        boolean selected=size==DEFAULT_SIZE;
        JRadioButton button=new JRadioButton(name,selected);
        group.add(button);
        buttonPanel.add(button);
        //Этот приёмник событий устанавливает размер шрифта для образцового текста метки
        ActionListener listener=event->label.setFont(new Font("Serif",Font.PLAIN,size));
        button.addActionListener(listener);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            RadioButtonFrame frame=new RadioButtonFrame();
            frame.setTitle("RadioButton");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
