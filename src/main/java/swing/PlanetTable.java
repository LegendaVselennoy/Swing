package swing;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;

public class PlanetTable {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            var frame=new PlanetTableFrame1();
            frame.setTitle("PlanetTable");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 *  Этот фрейм содержит таблицы с данными о планетах
 */
class PlanetTableFrame1 extends JFrame{

    private static final int DEFAULT_WIDTH=600;
    private static final int DEFAULT_HEIGHT=500;
    private static final int COLOR_COLUMN=4;
    private static final int IMAGE_COLUMN=5;

    private JTable table;
    private HashSet<Integer>removedRowIndices;
    private ArrayList<TableColumn>removedColumns;
    private JCheckBoxMenuItem columnsItem;
    private JCheckBoxMenuItem rowsItem;
    private JCheckBoxMenuItem cellsItem;

    private String[] columnNames={"Planet","Radius","Moons","Gaseous","Color","Image"};

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

    public PlanetTableFrame1(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        var model=new DefaultTableModel(cells,columnNames){
            public Class<?>getColumnClass(int c){
                return cells[0][c].getClass();
            }
        };
        table=new JTable(model);
        table.setRowHeight(100);
        table.getColumnModel().getColumn(COLOR_COLUMN).setMinWidth(250);
        table.getColumnModel().getColumn(IMAGE_COLUMN).setMinWidth(100);

        var sorter=new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setComparator(COLOR_COLUMN, Comparator.comparing(Color::getBlue)
                .thenComparing(Color::getGreen)
                .thenComparing(Color::getRed));
        sorter.setSortable(IMAGE_COLUMN,false);
        add(new JScrollPane(table),BorderLayout.CENTER);
        removedRowIndices=new HashSet<>();
        removedColumns=new ArrayList<>();

        var filter=new RowFilter<TableModel,Integer>(){
            public boolean include(Entry<? extends TableModel,? extends Integer>entry){
                return !removedRowIndices.contains(entry.getIdentifier());
            }
        };
        // Создать меню
        var menuBar=new JMenuBar();
        setJMenuBar(menuBar);

        var selectionMenu=new JMenu("Selection");
        menuBar.add(selectionMenu);

        rowsItem=new JCheckBoxMenuItem("Rows");
        columnsItem=new JCheckBoxMenuItem("Columns");
        cellsItem=new JCheckBoxMenuItem("Cells");

        rowsItem.setSelected(table.getRowSelectionAllowed());
        columnsItem.setSelected(table.getColumnSelectionAllowed());
        cellsItem.setSelected(table.getCellSelectionEnabled());

        rowsItem.addActionListener(event->{
            table.clearSelection();
            table.setRowSelectionAllowed(rowsItem.isSelected());
            updateCheckboxMenuItems();
        });

        columnsItem.addActionListener(event->{
            table.clearSelection();
            table.setColumnSelectionAllowed(columnsItem.isSelected());
            updateCheckboxMenuItems();
        });
        selectionMenu.add(columnsItem);

        columnsItem.addActionListener(event->{
            table.clearSelection();
            table.setCellSelectionEnabled(cellsItem.isSelected());
            updateCheckboxMenuItems();
        });
        selectionMenu.add(cellsItem);

        var tableMenu=new JMenu("Edit");
        menuBar.add(tableMenu);

        var hideColumnsItem=new JMenuItem("Hide Columns");
        hideColumnsItem.addActionListener(event->{
            int[] selected=table.getSelectedColumns();
            TableColumnModel columnModel=table.getColumnModel();
            // удалить столбцы из представления таблицы,
            // начиная с последнего индекса, но не затрагивая номера столбцов
            for (int i = selected.length-1; i >=0 ; i--) {
                TableColumn column=columnModel.getColumn(selected[i]);
                table.removeColumn(column);
                // сохранить удалённые столбцы для отображения
                removedColumns.add(column);
            }
        });
        tableMenu.add(hideColumnsItem);

        var showColumnsItem=new JMenuItem("Show Columns");
        showColumnsItem.addActionListener(event->{
            // восстановить все удалённые столбцы
            for (TableColumn tc : removedColumns)
                table.addColumn(tc);
                removedColumns.clear();
        });
        tableMenu.add(showColumnsItem);

        var hideRowsItem=new JMenuItem("Hide Rows");
        hideRowsItem.addActionListener(event->{
            int[] selected=table.getSelectedRows();
            for (int i : selected)
                removedRowIndices.add(table.convertRowIndexToModel(i));
            sorter.setRowFilter(filter);
        });
        tableMenu.add(hideRowsItem);

        var showRowsItem=new JMenuItem("Show Rows");
        showRowsItem.addActionListener(event->{
            removedRowIndices.clear();
            sorter.setRowFilter(filter);
        });
        tableMenu.add(showRowsItem);

        var printSelectionItem=new JMenuItem("Print Selection");
        printSelectionItem.addActionListener(event->{
            int [] selected=table.getSelectedRows();
            System.out.println("Selected rows: "+ Arrays.toString(selected));
        });
        tableMenu.add(printSelectionItem);
    }

    private void updateCheckboxMenuItems(){
        rowsItem.setSelected(table.getRowSelectionAllowed());
        columnsItem.setSelected(table.getColumnSelectionAllowed());
        cellsItem.setSelected(table.getCellSelectionEnabled());
    }
}
