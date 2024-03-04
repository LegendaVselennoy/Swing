package swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Фрейм со строкой меню, при выборе команды File->Connect из которого появляется
 * диалоговое окно для ввода пароля
 */
public class DataExchangeFrame extends JFrame {
    private static final int TEXT_ROWS=20;
    private static final int TEXT_COLUMNS=40;
    private PasswordChooser  dialog=null;
    private JTextArea textArea;

    public DataExchangeFrame(){
        //Сконструировать меню File
        JMenuBar mbar=new JMenuBar();
        setJMenuBar(mbar);
        JMenu fileMenu=new JMenu("File");
        mbar.add(fileMenu);
        //Ввести в меню пункты Connect и Exit
        JMenuItem connectItem=new JMenuItem("Connect");
        connectItem.addActionListener(new ConnectAction());
        fileMenu.add(connectItem);
        //При выборе пункта меню Exit происходит выход из программы
        JMenuItem exitItem=new JMenuItem("Exit");
        exitItem.addActionListener(event->System.exit(0));
        fileMenu.add(exitItem);
        JTextArea textArea=new JTextArea(TEXT_ROWS,TEXT_COLUMNS);
        add(new JScrollPane(textArea),BorderLayout.CENTER);
        pack();
    }

    /**
     * При выполнении команды Connect появляется диалоговое окно для ввода пароля
     */
    private class ConnectAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event) {
            //При первом обращении конструируется диалоговое окно
            if (dialog==null)
                dialog=new PasswordChooser();
            //Установить значения по умолчанию
//            dialog.setUser(new User("yourname"),null);
            //Показать диалоговое окно
            if (dialog.showDialog(DataExchangeFrame.this,"Connect")){
                //Если пользователь подтвердил введенные данные,
                //извлечь их для последующей обработки
//                User u=dialog.getUser();
                textArea.append("user name= "+
                        ", password= "+
                       // (new String(u.getPassword()))+
                        "\n");
            }
        }
    }

    /**
     * Окно для ввода пароля, отображаемое в диалоговом окне
     */
    public class PasswordChooser extends JPanel{

        private JTextField username;
        private JPasswordField password;
        private JButton okButton;
        private boolean ok;
        private JDialog dialog;


        public PasswordChooser(){
            setLayout(new BorderLayout());
            //Сконструировать панель с полями для ввода имени пользователя и пароля
            JPanel panel=new JPanel();
            panel.setLayout(new GridLayout(2,2));
            panel.add(new JLabel("User name: "));
            panel.add(username=new JTextField(""));
            panel.add(new JLabel("Password: "));
            panel.add(password=new JPasswordField(""));
            add(panel,BorderLayout.CENTER);
            //Создать кнопки OK и Cancel для закрытия диалогового окна
            okButton=new JButton("Ok");
            okButton.addActionListener(event->{
                ok=true;
                dialog.setVisible(false);
            });
            JButton cancelButton=new JButton("Cancel");
            cancelButton.addActionListener(event->dialog.setVisible(false));
            //Ввести кнопки в нижней части окна у южной его границы
            JPanel buttonPanel=new JPanel();
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            add(buttonPanel,BorderLayout.SOUTH);
        }
        /**
         * Устанавливает диалоговое окно в исходное состояние
         * @param u Данные о пользователе по умолчанию
         */
        public void setUser(User u){
            username.setText(u.getName());
        }
        /**
         * Получает данные, введённые в диалоговом окне
         * @return Объект типа User, состояние которого отражает введённые пользователем данные
         */
//        public User getUser(){
//            return new User(username.getText(),password.getPassword());
//        }
        /**
         * Отображает панель для ввода пароля в диалоговом окне
         * @param parent Компонент из фрейма-владельца или пустое значение null
         * @param title Заголовок диалогового окна
         */
        public boolean showDialog(Component parent,String title){
            ok=false;
            //Обнаружить фрейм-владелец
            Frame owner=null;
            if (parent instanceof Frame)
                owner=(Frame) parent;
            else
                owner=(Frame) SwingUtilities.getAncestorOfClass(Frame.class,parent);
            //Создать новое диалоговое окно при первом обращении или изменении фрейма-владельца
            if (dialog==null || dialog.getOwner()!=owner){
                dialog=new JDialog(owner,true);
                dialog.add(this);
                dialog.getRootPane().setDefaultButton(okButton);
                dialog.pack();
            }
            //Установить заголовок и отобразить диалоговое окно
            dialog.setTitle(title);
            dialog.setVisible(true);
            return ok;
        }
    }
    public class User{
        private String name;
        private int password;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPassword() {
            return password;
        }

        public void setPassword(int password) {
            this.password = password;
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            DataExchangeFrame frame=new DataExchangeFrame();
            frame.setTitle("Exchange");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
