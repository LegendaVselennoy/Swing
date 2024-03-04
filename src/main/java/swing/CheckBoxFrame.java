package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Фрейм с меткой образцового текста и флажками для выбора шрифта
 * attributes
 */
public class CheckBoxFrame extends JFrame {
    private JLabel label;
    private JCheckBox bold;
    private JCheckBox italic;
    private static final int FONT_SIZE=24;

    public CheckBoxFrame(){
        //Ввести метку образцового текста
        label=new JLabel("The quick brown fox jumps over the lazy dog.");
        label.setFont(new Font("Serif",Font.BOLD,FONT_SIZE));
        add(label,BorderLayout.CENTER);

        //В этом приёмнике событий устанавливается атрибут шрифта для воспроизведения метки по состоянию флажка
        ActionListener listener=event->{
            int mode=0;
            if (bold.isSelected()) mode+=Font.BOLD;
            if (italic.isSelected()) mode+=Font.ITALIC;
            label.setFont(new Font("Serif",mode,FONT_SIZE));
        };
        //Ввести флажки
        JPanel buttonPanel=new JPanel();
        bold=new JCheckBox("Bold");
        bold.addActionListener(listener);
        bold.setSelected(true);
        buttonPanel.add(bold);

        italic=new JCheckBox("Italic");
        italic.addActionListener(listener);
        buttonPanel.add(italic);

        add(buttonPanel,BorderLayout.SOUTH);
        pack();
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            CheckBoxFrame frame=new CheckBoxFrame();
            frame.setTitle("Text");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
