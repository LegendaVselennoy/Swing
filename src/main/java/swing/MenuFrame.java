package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Фрейм с образцом строки меню
 */
public class MenuFrame extends JFrame {

    private static final int DEFAULT_WIDTH=300;
    private static final int DEFAULT_HEIGHT=200;
    private Action saveAction;
    private Action saveAsAction;
    private JCheckBoxMenuItem readonlyItem;
    private JPopupMenu popup;

    /**
     * Обработчик действий, выводящий имя действия в стандартный поток System.out
     */
    static class TestAction extends AbstractAction{
        public TestAction(String name){
            super(name);
        }
        @Override
        public void actionPerformed(ActionEvent event) {
            System.out.println(getValue(Action.NAME)+" selected");
        }
    }
    public MenuFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        JMenu fileMenu=new JMenu("File");
        fileMenu.add(new TestAction("New"));
        //Продемонстрировать применение оперативных клавиш
        JMenuItem openItem=fileMenu.add(new TestAction("Open"));
        openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        fileMenu.addSeparator();

        saveAction=new TestAction("Save");
        JMenuItem saveItem=fileMenu.add(saveAction);
        saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

        saveAsAction=new TestAction("Save As");
        fileMenu.add(saveAsAction);
        fileMenu.addSeparator();

        fileMenu.add(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });
        //Продемонстрировать применение флажков и кнопок-переключателей
        readonlyItem=new JCheckBoxMenuItem("Read only");
        readonlyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                boolean saveOk=!readonlyItem.isSelected();
                saveAction.setEnabled(saveOk);
                saveAsAction.setEnabled(saveOk);
            }
        });
        ButtonGroup group=new ButtonGroup();
        JRadioButtonMenuItem insertItem=new JRadioButtonMenuItem("Insert");
        insertItem.setSelected(true);
        JRadioButtonMenuItem overTypeItem=new JRadioButtonMenuItem("OverType");
        group.add(insertItem);
        group.add(overTypeItem);

        //Продемонстрировать применение пиктограмм
        TestAction cutAction=new TestAction("Cut");
        cutAction.putValue(Action.SMALL_ICON,new ImageIcon("cut.gif"));
        TestAction copyAction=new TestAction("Copy");
        copyAction.putValue(Action.SMALL_ICON,new ImageIcon("copy.gif"));
        TestAction pasteAction=new TestAction("Paste");
        pasteAction.putValue(Action.SMALL_ICON,new ImageIcon("paste.gif"));

        JMenu editMenu=new JMenu("Edit");
        editMenu.add(cutAction);
        editMenu.add(copyAction);
        editMenu.add(pasteAction);

        //Продемонстрировать применение вложенных меню
        JMenu optionMenu=new JMenu("Options");
        optionMenu.add(readonlyItem);
        optionMenu.addSeparator();
        optionMenu.add(insertItem);
        optionMenu.add(overTypeItem);

        editMenu.addSeparator();
        editMenu.add(optionMenu);

        //Продемонстрировать применение клавиш быстрого доступа
        JMenu helpMenu=new JMenu("Help");
        helpMenu.setMnemonic('H');

        JMenuItem indexItem=new JMenuItem("Index");
        indexItem.setMnemonic('I');
        helpMenu.add(indexItem);

        //Назначить клавишу быстрого доступа, используя объект действия
//        TestAction aboutAction=new TestAction("About");
//        aboutAction.putValue(Action.MNEMONIC_KEY,new Integer('A'));
//        helpMenu.add(aboutAction);

        //Ввести все меню верхнего уровня в строку меню
        JMenuBar menuBar=new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        //Продемонстрировать применение всплывающих меню
        popup=new JPopupMenu();
        popup.add(cutAction);
        popup.add(copyAction);
        popup.add(pasteAction);

        JPanel panel=new JPanel();
        panel.setComponentPopupMenu(popup);
        add(panel);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            MenuFrame frame=new MenuFrame();
            frame.setTitle("Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
