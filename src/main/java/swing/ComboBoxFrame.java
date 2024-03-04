package swing;

import javax.swing.*;
import java.awt.*;

/**
 * Фрейм с образцовым текстом метки и комбинированным списком для выбора начертаний шрифта
 */
public class ComboBoxFrame extends JFrame {
    private JComboBox<String> faceCombo;
    private JLabel label;
    private static final int DEFAULT_SIZE=24;

    public ComboBoxFrame(){
        //Ввести метку с образцовым текстом
        label=new JLabel("The quick brown fox jumps over the lazy dog.");
        label.setFont(new Font("Serif",Font.PLAIN,DEFAULT_SIZE));
        add(label,BorderLayout.CENTER);

        //Составить комбинированный список и ввести в него названия начертаний шрифта
        faceCombo=new JComboBox<>();
        faceCombo.addItem("Serif");
        faceCombo.addItem("SansSerif");
        faceCombo.addItem("Monospaced");
        faceCombo.addItem("Dialog");
        faceCombo.addItem("DialogInput");

        //Приёмник событий от комбинированного списка изменяет на выбранное начертание шрифта, которым набран текст метки
        faceCombo.addActionListener(event->label.setFont(new Font(faceCombo.getItemAt(
                faceCombo.getSelectedIndex()),
                 Font.PLAIN,DEFAULT_SIZE)));

        //Ввести комбинированный список на панели у южной границы фрейма
        JPanel comboPanel=new JPanel();
        comboPanel.add(faceCombo);
        add(comboPanel,BorderLayout.SOUTH);
        pack();
     }
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            ComboBoxFrame frame=new ComboBoxFrame();
            frame.setTitle("ComboBox");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
