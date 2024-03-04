package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class CompositeTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            var frame=new CompositeTestFrame();
            frame.setTitle("CompositeTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * Этот фрейм содержит комбинированный список для выбора правил, композиции, ползунок для изменения
 * прозрачности в альфа-канале исходного изображения, а также компонент для отображения результатов
 * составления изображений
 */
class CompositeTestFrame extends JFrame{
    private static final int DEFAULT_WIDTH=400;
    private static final int DEFAULT_HEIGHT=400;
    private CompositeComponent canvas;
    private JComboBox<Rule>ruleCombo;
    private JSlider alphaSlider;
    private JTextField explanation;

    public CompositeTestFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        canvas=new CompositeComponent();
        add(canvas, BorderLayout.CENTER);

        ruleCombo=new JComboBox<>(new Rule[]{
                new Rule("CLEAR"," "," "),
                new Rule("SRC"," S"," S"),
                new Rule("DST"," ","DD"),
                new Rule("SRC_OVER"," S","DS"),
                new Rule("DST_OVER"," S","DD"),
                new Rule("SRC_IN"," "," S"),
                new Rule("SRC_OUT"," S"," "),
                new Rule("DST_IN"," "," D"),
                new Rule("DST_OUT"," ","D "),
                new Rule("SRC_ATOP"," ","DS"),
                new Rule("DST_ATOP"," S"," D"),
                new Rule("XOR"," S","D "), });
        ruleCombo.addActionListener(event->{
            var r=(Rule) ruleCombo.getSelectedItem();
            canvas.setRule(r.getValue());
            explanation.setText(r.getExplanation());
        });
        alphaSlider=new JSlider(0,100,75);
        alphaSlider.addChangeListener(event->
                canvas.setAlpha(alphaSlider.getValue()));
        var panel=new JPanel();
        panel.add(ruleCombo);
        panel.add(new JLabel("Alpha"));
        panel.add(alphaSlider);
        add(panel,BorderLayout.NORTH);

        explanation=new JTextField();
        add(explanation,BorderLayout.SOUTH);

        canvas.setAlpha(alphaSlider.getValue());
        Rule r=ruleCombo.getItemAt(
                ruleCombo.getSelectedIndex());
                canvas.setRule(r.getValue());
                explanation.setText(r.getExplanation());
    }
}

/**
 * Этот компонент рисует две формы, составленные по правилу композиции
 */
class CompositeComponent extends JComponent{
    private int rule;
    private Shape shape1;
    private Shape shape2;
    private float alpha;

    public CompositeComponent(){
        shape1=new Ellipse2D.Double(100,100,150,100);
        shape2=new Ellipse2D.Double(150,150,150,100);
    }

    public void paintComponent(Graphics g){
        var g2=(Graphics2D) g;
        var image=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D gImage=image.createGraphics();
        gImage.setPaint(Color.red);
        gImage.fill(shape1);
        AlphaComposite composite=AlphaComposite.getInstance(rule,alpha);
        gImage.setComposite(composite);
        gImage.setPaint(Color.blue);
        gImage.fill(shape2);
        g2.drawImage(image,null,0,0);
    }

    /**
     * Устанавливает правило композиции
     * @param r Правило композиции (в виде константы из класса AlphaComposite)
     */
    public void setRule(int r){
        rule=r;
        repaint();
    }

    /**
     * Устанавливает значение прозрачности в альфа-канале исходного изображения
     * @param a Значение прозрачности в пределах от 0 до 100
     */
    public void setAlpha(int a){
        alpha=(float) a/100.0F;
        repaint();
    }
}

/**
 * Этот класс описывает правило композиции Портера-Даффа
 */
class Rule{
    private String name;
    private String porterDuff1;
    private String porterDuff2;

    /**
     * Составляет правило композиции Портера-Даффа
     * @param n Название правила композиции
     * @param pd1 Первый ряд квадратов в правиле композиции Портера-Даффа
     * @param pd2 Второй ряд в квадратах правиле композиции Портера-Даффа
     */
    public Rule(String n,String pd1,String pd2){
        name=n;
        porterDuff1=pd1;
        porterDuff2=pd2;
    }

    /**
     * Получает объяснение композиции по данному правилу
     * @return Объяснение композиции по данному правилу
     */
    public String getExplanation(){
        var r=new StringBuilder("Source ");
        if (porterDuff2.equals(" "))
            r.append("clears");
        if (porterDuff2.equals(" S"))
            r.append("overwrites");
        if (porterDuff2.equals("DS"))
            r.append("blends with");
        if (porterDuff2.equals(" D"))
            r.append("alpha modifies");
        if (porterDuff2.equals("D "))
            r.append("alpha complement modifies");
        if (porterDuff2.equals("DD"))
            r.append("does not affect");
        r.append(" destination");
        if (porterDuff1.equals(" S"))
            r.append(" and overwrites empty pixels");
        r.append(".");
        return r.toString();
    }

    public String toString(){
        return name;
    }

    /**
     * Получает значение по данному правилу в классе AlphaComposite
     * @return Значение константы из класса AlphaComposite или значение -1, если
     * соответствующая константа отсутствует
     */
    public int getValue(){
        try{
            return (Integer) AlphaComposite.class.getField(name).get(null);
        }catch (Exception e){
            return -1;
        }
    }
}