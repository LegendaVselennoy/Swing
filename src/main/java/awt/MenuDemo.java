package awt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuDemo implements ActionListener {
    JLabel label;
    JPopupMenu jpu;
    DebugAction setAct;
    DebugAction clearAct;
    DebugAction resumeAct;

    ImageIcon setIcon=new ImageIcon("iconps.png");
    ImageIcon clearIcon=new ImageIcon("jsicon.png");
    ImageIcon resumeIcon=new ImageIcon("javaicon.png");


    MenuDemo(){
        JFrame frame=new JFrame("Menu Demo");
//        frame.setLayout(new FlowLayout());
        frame.setSize(220,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Создать метку, в которой будет отображаться выбор меню
        label=new JLabel();

        frame.add(label,BorderLayout.CENTER);

        JMenuBar jMenuBar=new JMenuBar();

        setAct=new DebugAction("Set Breakpoint",
                                setIcon,
                                KeyEvent.VK_S,
                                 KeyEvent.VK_B,
                                 "Set a break point.");

        clearAct=new DebugAction("Clear Breakpoint",
                clearIcon,
                KeyEvent.VK_C,
                KeyEvent.VK_L,
                "CLear a break point.");

        resumeAct=new DebugAction("Resume",
                resumeIcon,
                KeyEvent.VK_R,
                KeyEvent.VK_R,
                "Resume breakpoint");

        clearAct.setEnabled(false);

        JButton jbtnSet=new JButton(setAct);
        JButton jbtnCLear=new JButton(clearAct);
        JButton jbtnResume=new JButton(resumeAct);

        JToolBar jtb=new JToolBar("Breakpoints");

        jtb.add(jbtnSet);
        jtb.add(jbtnCLear);
        jtb.add(jbtnResume);

        frame.add(jtb,BorderLayout.NORTH);

        JMenu jmDebug=new JMenu("Debug");

        JMenuItem jmiSetBP=new JMenuItem(setAct);
        JMenuItem jmiClearBP=new JMenuItem(clearAct);
        JMenuItem jmiResume=new JMenuItem(resumeAct);
        jmDebug.add(jmiSetBP);
        jmDebug.add(jmiClearBP);
        jmDebug.add(jmiResume);



        JMenu jmFile=new JMenu("File");
        // Создать меню File с мнемоническими символами и клавиатурными сочетания
        jmFile.setMnemonic(KeyEvent.VK_F);  // alt+f
        JMenuItem jmiOpen=new JMenuItem("Open",KeyEvent.VK_O); // ctrl+сочетание
        jmiOpen.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        JMenuItem jmiClose=new JMenuItem("Close",KeyEvent.VK_C);
        jmiClose.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        JMenuItem jmiSave=new JMenuItem("Save",KeyEvent.VK_S);
        jmiSave.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        JMenuItem jmiExit=new JMenuItem("Exit",KeyEvent.VK_E);
        jmiExit.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));

        jpu=new JPopupMenu();

//        JToolBar jtb=new JToolBar("Debug");



        ImageIcon set=new ImageIcon("src/awt/swing/jsicon.png");
        ImageIcon clear=new ImageIcon("src/awt/swing/iconps.png");
        ImageIcon resume=new ImageIcon("src/awt/swing/javaicon.png");

//        JButton jbtnSet=new JButton(set);
//        jbtnSet.setActionCommand("Set Breakpoint");
//        jbtnSet.setToolTipText("Set Breakpoint");
//        JButton jbtnClear=new JButton(clear);
//        jbtnClear.setActionCommand("Clear Breakpoint");
//        jbtnClear.setToolTipText("Clear Breakpoint");
//        JButton jbtnResume=new JButton(resume);
//        jbtnResume.setActionCommand("Resume");
//        jbtnResume.setToolTipText("Resume");
//
//        jtb.add(jbtnSet);
//        jtb.add(jbtnClear);
//        jtb.add(jbtnResume);
//
//        frame.add(jtb,BorderLayout.NORTH);


        JMenuItem jmiCut=new JMenuItem("Cut");
        JMenuItem jmiCopy=new JMenuItem("Copy");
        JMenuItem jmiPaste=new JMenuItem("Paste");
        jpu.add(jmiCut);
        jpu.add(jmiCopy);
        jpu.add(jmiPaste);

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger())
                    jpu.show(e.getComponent(),e.getX(),e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger())
                    jpu.show(e.getComponent(),e.getX(),e.getY());
            }
        });

        jmFile.add(jmiOpen);
        jmFile.add(jmiClose);
        jmFile.add(jmiSave);
        jmFile.addSeparator();
        jmFile.add(jmiExit);
        jMenuBar.add(jmFile);

        JMenu jmOptions=new JMenu("Options");
        JMenu jmColors=new JMenu("Colors");

        JCheckBoxMenuItem jmiRed=new JCheckBoxMenuItem("Red");
        JCheckBoxMenuItem jmiGreen=new JCheckBoxMenuItem("Green");
        JCheckBoxMenuItem jmiBlue=new JCheckBoxMenuItem("Blue");

//        JMenuItem jmiRed=new JMenuItem("Red");
//        JMenuItem jmiGreen=new JMenuItem("Green");
//        JMenuItem jmiBlue=new JMenuItem("Blue");
        jmColors.add(jmiRed);
        jmColors.add(jmiGreen);
        jmColors.add(jmiBlue);
        jmOptions.add(jmColors);

        jmOptions.add(jmDebug);

        JMenu jmPriority=new JMenu("Priority");

        JRadioButtonMenuItem jmiHigh=new JRadioButtonMenuItem("High",true);
        JRadioButtonMenuItem jmiLow=new JRadioButtonMenuItem("Low");
//        JMenuItem jmiHigh=new JMenuItem("High");
//        JMenuItem jmiLow=new JMenuItem("Low");
        jmPriority.add(jmiHigh);
        jmPriority.add(jmiLow);
        jmOptions.add(jmPriority);

        ButtonGroup bg=new ButtonGroup();
        bg.add(jmiHigh);
        bg.add(jmiLow);

        JMenuItem jmiReset=new JMenuItem("Reset");
        jmOptions.addSeparator();
        jmOptions.add(jmiReset);

        jMenuBar.add(jmOptions);

        JMenu jmHelp=new JMenu("Help");
        ImageIcon icon=new ImageIcon("src/awt/swing/javaicon.png");
        JMenuItem jmiAbout=new JMenuItem("About",icon);
        jmiAbout.setToolTipText("Info about java"); // всплывающая подсказка
        jmHelp.add(jmiAbout);
        jMenuBar.add(jmHelp);

        jmiOpen.addActionListener(this);
        jmiClose.addActionListener(this);
        jmiSave.addActionListener(this);
        jmiExit.addActionListener(this);
        jmiRed.addActionListener(this);
        jmiGreen.addActionListener(this);
        jmiBlue.addActionListener(this);
        jmiHigh.addActionListener(this);
        jmiLow.addActionListener(this);
        jmiReset.addActionListener(this);
        jmiAbout.addActionListener(this);
        jmiCut.addActionListener(this);
        jmiCopy.addActionListener(this);
        jmiPaste.addActionListener(this);
        jbtnSet.addActionListener(this);
//        jbtnClear.addActionListener(this);
        jbtnResume.addActionListener(this);

        frame.add(label);
        frame.setJMenuBar(jMenuBar);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Получить команду действия из выбора меню
        String comStr=e.getActionCommand();
        // Если пользователь выбрал Exit, тогда завершить программу
        if (comStr.equals("Exit")) System.exit(0);
        // Иначе отобразить выбор
        label.setText(comStr+" Selected");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuDemo::new);
    }

    class DebugAction extends AbstractAction{
        public DebugAction(String name,Icon image,int mem,int accel,String tTip){
            super(name,image);
            putValue(ACCELERATOR_KEY,
                    KeyStroke.getKeyStroke(accel,InputEvent.CTRL_DOWN_MASK));
            putValue(MNEMONIC_KEY,mem);
            putValue(SHORT_DESCRIPTION,tTip);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String comStr=e.getActionCommand();
            label.setText(comStr+" Selected");

            if (comStr.equals("Set Breakpoint")){
                clearAct.setEnabled(true);
                setAct.setEnabled(false);
            }else if (comStr.equals("Clear Breakpoint")){
                clearAct.setEnabled(false);
                setAct.setEnabled(true);
            }
        }
    }
}


