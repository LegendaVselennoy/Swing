package swing;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.util.EventObject;

public class TableCell {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            var frame=new TableCellRenderFrame();
            frame.setTitle("TableCell");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 *  Этот фрейм содержит таблицу с данными о планетах
 */
class TableCellRenderFrame extends JFrame{

    private static final int DEFAULT_WIDTH=600;
    private static final int DEFAULT_HEIGHT=400;

    public TableCellRenderFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        var model=new PlanetTableModel();
        var table=new JTable(model);
        table.setRowSelectionAllowed(false);

        // Установить средства воспроизведения и редактирования ячеек таблицы
        table.setDefaultRenderer(Color.class,new ColorTableCellRenderer());
        table.setDefaultEditor(Color.class,new ColorTableCellEditor());
        var moonCombo=new JComboBox<>();
        for (int i = 0; i <=20; i++)
            moonCombo.addItem(i);

        TableColumnModel columnModel=table.getColumnModel();
//        TableColumnModel moonColumn=columnModel.getColumn(PlanetTableModel.MOONS_COLUMN);
//        moonColumn.setCellEditor(new DefaultCellEditor(moonCombo));
//        moonColumn.setHeaderRenderer(table.getDefaultRenderer(ImageIcon.class));
//        moonColumn.setHeaderValue(new ImageIcon(getClass().getResource("Moons.gif")));
        // показать таблицу
        table.setRowHeight(100);
        add(new JScrollPane(table),BorderLayout.CENTER);
    }
}

class PlanetTableModel extends AbstractTableModel{

    public static final int PLANET_COLUMN=0;
    public static final int MOONS_COLUMN=2;
    public static final int GASEOUS_COLUMN=3;
    public static final int COLOR_COLUMN=4;

    private Object[][] cells={
            {"Mercury",2440.0,0,false, Color.YELLOW,new ImageIcon(getClass().getResource("Mercury.gif"))},
            {"Venus",6052.0,0,false,Color.YELLOW,new ImageIcon(getClass().getResource("Venus.gif"))},
            {"Earth",6378.0,1,false,Color.BLUE,new ImageIcon(getClass().getResource("Earth.gif"))},
            {"Mars",3397.0,2,false,Color.RED,new ImageIcon(getClass().getResource("Mars.gif"))},
            {"Jupiter",71492.0,16,true,Color.ORANGE,new ImageIcon(getClass().getResource("Jupiter.gif"))},
            {"Saturn",60268.0,18,true,Color.ORANGE,new ImageIcon(getClass().getResource("Saturn.gif"))},
            {"Uranus",25559.0,17,true,Color.BLUE,new ImageIcon(getClass().getResource("Uranus.gif"))},
            {"Neptune",24766.0,8,true,Color.BLUE,new ImageIcon(getClass().getResource("Neptune.gif"))},
            {"Pluto",1137.0,1,false,Color.BLACK,new ImageIcon(getClass().getResource("Pluto.gif"))}
    };

    private String[] columnNames={"Planet","Radius","Moons","Gaseous","Color","Image"};

    public String getColumnName(int c){
        return columnNames[c];
    }

    public Class<?>getColumnClass(int c){
        return cells[0][c].getClass();
    }

    public int getColumnCount(){
        return cells[0].length;
    }

    public int getRowCount(){
        return cells.length;
    }

    public Object getValueAt(int r,int c){
        return cells[r][c];
    }

    public void setValueAt(Object obj,int r,int c){
        cells[r][c]=obj;
    }

    public boolean isCellEditable(int r,int c){
        return c==PLANET_COLUMN || c==MOONS_COLUMN || c==GASEOUS_COLUMN || c==COLOR_COLUMN;
    }
}

/**
 *  Это средство воспроизведения отображает цвет в виде панели с заданным цветом
 */
class ColorTableCellRenderer extends JPanel implements TableCellRenderer{

    public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,
                                                   boolean hasFocus,int row,int column){
        setBackground((Color) value);
        if (hasFocus)
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        else  setBorder(null);
        return this;
    }
}

class ColorTableCellEditor extends AbstractCellEditor implements TableCellEditor{
    private JColorChooser colorChooser;
    private JDialog colorDialog;
    private JPanel panel;

    public ColorTableCellEditor(){
        panel=new JPanel();
        // подготовить диалоговое окно выбора цвета
        colorChooser=new JColorChooser();
        colorDialog=JColorChooser.createDialog(null,"Planet Color",false,colorChooser,
                EventHandler.create(ActionListener.class,this,"stopCellEditing"),
                EventHandler.create(ActionListener.class,this,"cancelCellEditing"));
    }

    public Component getTableCellEditorComponent(JTable table,Object value,boolean isSelected,int row,int column){
        // Именно здесь получается текущее значение цвета, сохраняемое в диалоговом окне на тот случай,
        // если пользователь начнёт редактирование
        colorChooser.setColor((Color) value);
        return panel;
    }

    public boolean shouldSelectCell(EventObject anEvent){
        // начать редактирование
        colorDialog.setVisible(true);
        // уведомить вызывающую часть программы о том,
        // что данную ячейку разрешается выбрать
        return true;
    }

    public void cancelCellEditing(){
        // редактирование отменено - скрыть диалоговое окно
        colorDialog.setVisible(false);
        super.cancelCellEditing();
    }

    public boolean stopCellEditing(){
        // редактирование завершено - скрыть диалоговое окно
        colorDialog.setVisible(false);
        super.stopCellEditing();
        // уведомить вызывающую часть программы о том, что данное значение цвета разрешается использовать
        return true;
    }

    public Object getCellEditorValue(){
        return colorChooser.getColor();
    }
}


