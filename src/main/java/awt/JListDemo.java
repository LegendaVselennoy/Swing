package awt;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class JListDemo {

    String[] cities={"New York","Chicago","Houston",
            "Denver","Los Angeles","Seattle",
            "London","Paris","New Delhi",
            "Hong Kong","Tokyo","Sydney"};

    public JListDemo(){
        JFrame jfrm=new JFrame("JListDemo");
        jfrm.setLayout(new FlowLayout());
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setSize(200,200);

        JList<String>jList=new JList<>(cities);

        //  Установить режим выбора списка в одиночный выбор
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane jscrlp=new JScrollPane(jList);

        // Установить предпочитаемые размеры прокручиваемой панели
        jscrlp.setPreferredSize(new Dimension(120,90));

        JLabel jLab=new JLabel("Выберите город");

        // Добавить прослушиватель событий выбора для списка
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Получить индекс измененного элемента
                int idx=jList.getSelectedIndex();
                // Если элемент был выбран, тогда отобразить выбор
                if (idx!=-1){
                    jLab.setText("Текущий выбор: "+cities[idx]);
                }else {
                    // 8 противном случае запросить выбор заново
                    jLab.setText("Выберите город");
                }
            }
        });
        jfrm.add(jscrlp);
        jfrm.add(jLab);
        jfrm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JListDemo::new);
    }
}
