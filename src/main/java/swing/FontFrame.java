package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Фрейм, в котором сеточно-контейнерная компоновка служит для расположения компонентов,
 * предназначенных для выбора шрифтов
 */
public class FontFrame extends JFrame {
    private static final int TEXT_ROWS=10;
    private static final int TEXT_COLUMNS=20;
    private JComboBox<String> face;
    private JComboBox<Integer> size;
    private JCheckBox bold;
    private JCheckBox italic;
    private JTextArea sample;

    public FontFrame(){
        GridBagLayout layout=new GridBagLayout();
        setLayout(layout);
        ActionListener listener=event->updateSample();
        //Сконструировать компоненты
        JLabel faceLabel=new JLabel("Face: ");
        face=new JComboBox<>(new String[]{
                "Serif","SansSerif","Monospaced","Dialog","DialogInput"
        });
        face.addActionListener(listener);
        JLabel sizeLabel=new JLabel("Size: ");
        size=new JComboBox<>(new Integer[]{
                8,10,12,15,18,24,36,48
        });
        size.addActionListener(listener);

        bold=new JCheckBox("Bold");
        bold.addActionListener(listener);

        italic=new JCheckBox("Italic");
        italic.addActionListener(listener);

        sample=new JTextArea(TEXT_ROWS,TEXT_COLUMNS);
        sample.setText("Ok, Programmer");
        sample.setEnabled(false);
        sample.setLineWrap(true);
        sample.setBorder(BorderFactory.createEtchedBorder());

        //Ввести компоненты в сетку, используя служебный класс GBC
        add(faceLabel,new GBC(0,0).setAnchor(GBC.EAST));
        add(face,new GBC(1,0).setFill(GBC.HORIZONTAL).setWeight(100,0).setInsets(1));
        add(sizeLabel,new GBC(0,1).setAnchor(GBC.EAST));
        add(size,new GBC(1,1).setFill(GBC.HORIZONTAL).setWeight(100,0).setInsets(1));
        add(bold, new GBC(0,2,2,1).setAnchor(GBC.CENTER).setWeight(100,100));
        add(italic, new GBC(0,3,2,1).setAnchor(GBC.CENTER).setWeight(100,100));
        add(sample, new GBC(2,0,1,4).setFill(GBC.BOTH).setWeight(100,100));
        pack();
        updateSample();
    }
    public void updateSample(){
        String fontFace=(String) face.getSelectedItem();
        int fontStyle=(bold.isSelected()? Font.BOLD:0)+(italic.isSelected()?Font.ITALIC:0);
        int fontSize=size.getItemAt(size.getSelectedIndex());
        Font font=new Font(fontFace,fontStyle,fontSize);
        sample.setFont(font);
        sample.repaint();
    }

    static public class GBC extends GridBagConstraints{

        /**
         * Строит объект типа GBC, накладывая ограничения с помощью параметров gridx и gridy, а все остальные
         * ограничения накладываются на сеточно-контейнерную компоновку по умолчанию
         * @param gridx Местоположение в сетке по горизонтали
         * @param gridy Местоположение в сетке по вертикали
         */
        public GBC(int gridx,int gridy){
            this.gridx=gridx;
            this.gridy=gridy;
        }

        /**
         * Строит объект типа GBC, накладывая ограничения с помощью параметров gridx,gridy,gridwidth,gridheight,
         * а все остальные ограничения накладываются на сеточно-контейнерную компоновку по умолчанию
         * @param gridx Местоположение в сетке по горизонтали
         * @param gridy Местоположение в сетке по вертикали
         * @param gridwidth Шаг сетки по горизонтали
         * @param gridheight Шаг по сетки по вертикали
         */
       public GBC(int gridx,int gridy,int gridwidth,int gridheight){
           this.gridx=gridx;
           this.gridy=gridy;
           this.gridwidth=gridwidth;
           this.gridheight=gridheight;
       }

        /**
         * Устанавливает привязку к сетке
         * @param anchor Степень привязки
         * @return this Объект для последующего видоизменения
         */
       public GBC setAnchor(int anchor){
           this.anchor=anchor;
           return this;
       }

        /**
         * Устанавливает направление для заполнения
          * @param fill Направление заполнения
         * @return this Объект для последующего видоизменения
         */
       public GBC setFill(int fill){
           this.fill=fill;
           return this;
       }

        /**
         * Устанавливает веса ячеек
         * @param weightx Вес ячейки по горизонтали
         * @param weighty Вес ячейки по вертикали
         * @return this Объект для последующего видоизменения
         */
       public GBC setWeight(double weightx,double weighty){
           this.weightx=weightx;
           this.weighty=weighty;
           return this;
       }

        /**
         * Вводит пробелы вокруг данной ячейки
         * @param distance Пробел по всем направлениям
         * @return this Объект для последующего видоизменения
         */
       public GBC setInsets(int distance){
           this.insets=new Insets(distance,distance,distance,distance);
           return this;
       }

        /**
         * Вводит пробелы вокруг данной ячейки
         * @param top Пробел сверху
         * @param left Пробел слева
         * @param bottom Пробел снизу
         * @param right Пробел справа
         * @return this Объект для последующего видоизменения
         */
       public GBC setInsets(int top,int left,int bottom,int right){
           this.insets=new Insets(top,left,bottom,right);
           return this;
       }

        /**
         * Устанавливает внутреннее заполнение
         * @param ipadx Внутреннее заполнение по горизонтали
         * @param ipady Внутреннее заполнение по вертикали
         * @return this Объект для последующего видоизменения
         */
       public GBC setIpad(int ipadx,int ipady){
           this.ipadx=ipadx;
           this.ipady=ipady;
           return this;
       }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            FontFrame frame=new FontFrame();
            frame.setTitle("Font");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
