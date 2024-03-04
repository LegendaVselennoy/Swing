package swing;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

/**
 * В этой программе демонстрируется построение таблицы по её модели
 */

public class InvestmentTable {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            var frame=new InvestmentTableFrame();
            frame.setTitle("InvestmentTable");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 *  Этот фрейм содержит таблицу капиталовложений
 */
class InvestmentTableFrame extends JFrame{
    public InvestmentTableFrame(){
        var model=new InvestmentTableModel(30,5,10);
        var table=new JTable(model);
        add(new JScrollPane(table));
        pack();
    }

}

/**
 *  В этой модели таблицы рассчитывается содержимое ячеек таблицы всякий раз,
 *  когда оно запрашивается. В таблице представлен рост капиталовложений в
 *  течение ряда лет при разных учётных ставках
 */
class InvestmentTableModel extends AbstractTableModel{

    private static double INITIAL_BALANCE=100000.0;
    private int years;
    private int minRate;
    private int maxRate;

    /**
     * Конструирует модель таблицы капиталовложений
     * @param y Количество лет
     * @param r1 Наинизшая учётная ставка для составления таблицы
     * @param r2 Наивысшая учётная ставка для составления таблицы
     */
    public InvestmentTableModel(int y,int r1,int r2){
        years=y;
        minRate=r1;
        maxRate=r2;
    }

    public int getRowCount(){
        return years;
    }

    public int getColumnCount(){
        return maxRate-minRate+1;
    }

    public String getColumnName(int c){
        return (c+minRate)+"%";
    }

    public Object getValueAt(int r,int c){
        double rate=(c+minRate)/100.0;
        int nperiods=r;
        double futureBalance=INITIAL_BALANCE*Math.pow(1+rate,nperiods);
        return String.format("%.2f",futureBalance);
    }

}