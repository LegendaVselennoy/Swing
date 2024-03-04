package swing;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Hashtable;

/**
 * Фрейм с несколькими ползунками и текстовым полем для показа значений,
 * на которых по очереди устанавливаются ползунки
 */
public class SliderFrame extends JFrame {
    private final JPanel sliderPanel;
    private JTextField textField;
    private final ChangeListener listener;

    public SliderFrame(){
        sliderPanel=new JPanel();
        sliderPanel.setLayout(new GridLayout());

        //Общий приёмник событий для всех ползунков
        listener=event->{
            //Обновить текстовое поле, если выбранный ползунок установится на отметке с другим значением
            JSlider source=(JSlider) event.getSource();
            textField.setText(""+source.getValue());
        };
        //Ввести простой ползунок
        JSlider slider=new JSlider();
        addSlider(slider,"Plain");

        //Ввести ползунок с основными и неосновными отметками
        slider=new JSlider();
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMajorTickSpacing(5);
        addSlider(slider,"Ticks");

        //Ввести ползунок, привязываемый к отметкам
        slider=new JSlider();
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMajorTickSpacing(5);
        addSlider(slider,"Snap to ticks");

        //Ввести ползунок без отметок
        slider=new JSlider();
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMajorTickSpacing(5);
        slider.setPaintTrack(false);
        addSlider(slider,"No track");

        //Ввести обращённый ползунок
        slider=new JSlider();
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMajorTickSpacing(5);
        slider.setInverted(true);
        addSlider(slider,"Inverted");

        //Ввести ползунок с числовым обозначениями отметок
        slider=new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(20);
        slider.setMajorTickSpacing(5);
        addSlider(slider,"Labels");

        //Ввести ползунок с буквенными обозначениями отметок
        slider=new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(20);
        slider.setMajorTickSpacing(5);

        Hashtable labelTable=new Hashtable<Integer,Component>();
        labelTable.put(0,new JLabel("A"));
        labelTable.put(20,new JLabel("B"));
        labelTable.put(40,new JLabel("C"));
        labelTable.put(60,new JLabel("D"));
        labelTable.put(80,new JLabel("E"));
        labelTable.put(100,new JLabel("F"));

        slider.setLabelTable(labelTable);
        addSlider(slider,"Custom labels");

        //Ввести ползунок с пиктограммными обозначениями отметок
//        slider=new JSlider();
//        slider.setPaintTicks(true);
//        slider.setPaintLabels(true);
//        slider.setMajorTickSpacing(20);
//        slider.setMajorTickSpacing(20);

//        Hashtable labelTable1=new Hashtable<Integer,Component>();
        //Ввести изображения игральных карт
//        labelTable1.put(0,new JLabel(new ImageIcon("nine.gif")));
//        labelTable1.put(20,new JLabel(new ImageIcon("ten.gif")));
//        labelTable1.put(40,new JLabel(new ImageIcon("map.gif")));
//        labelTable1.put(60,new JLabel(new ImageIcon("queen.gif")));
//        labelTable1.put(80,new JLabel(new ImageIcon("king.gif")));
//        labelTable1.put(100,new JLabel(new ImageIcon("ace.gif")));

//        slider.setLabelTable(labelTable);
//        addSlider(slider,"Icon labels");

        //Вводит текстовое поле для показа значения,
        //на котором установлен выбранный в настоящий момент ползунок
        textField=new JTextField();
        add(sliderPanel,BorderLayout.CENTER);
        add(textField,BorderLayout.SOUTH);
        pack();
    }

    /**
     * Вводит ползунки на панели и привязывает к ним приёмник событий
     * @param s Ползунок
     * @param description Описание ползунка
     */
    public void addSlider(JSlider s,String description){
        s.addChangeListener(listener);
        JPanel panel=new JPanel();
        panel.add(s);
        panel.add(new JLabel(description));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridy=sliderPanel.getComponentCount();
        gbc.anchor=GridBagConstraints.WEST;
        sliderPanel.add(panel,gbc);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            SliderFrame frame=new SliderFrame();
            frame.setTitle("Slider");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
