package awt;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class JTreeDemo {
    public JTreeDemo(){
        JFrame jfrm=new JFrame("JTreeDemo");
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setSize(200,250);

        // Создать верхний узел дерева
        DefaultMutableTreeNode top=new DefaultMutableTreeNode("Options");

        // Создать поддерево "А"
        DefaultMutableTreeNode a=new DefaultMutableTreeNode("A");
        top.add(a);
        DefaultMutableTreeNode a1=new DefaultMutableTreeNode("A1");
        a.add(a1);
        DefaultMutableTreeNode a2=new DefaultMutableTreeNode("A2");
        a.add(a2);

        DefaultMutableTreeNode b=new DefaultMutableTreeNode("B");
        top.add(b);
        DefaultMutableTreeNode b1=new DefaultMutableTreeNode("B1");
        b.add(b1);
        DefaultMutableTreeNode b2=new DefaultMutableTreeNode("B2");
        b.add(b2);
        DefaultMutableTreeNode b3=new DefaultMutableTreeNode("B3");
        b.add(b3);

        // Создать дерево
        JTree tree=new JTree(top);

        JScrollPane jsp=new JScrollPane(tree);
        jfrm.add(jsp);

        JLabel jlab=new JLabel();
        jfrm.add(jlab, BorderLayout.SOUTH);

        // Обработать события выбора в дереве
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                jlab.setText("Выбран "+e.getPath());
            }
        });

        jfrm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JTreeDemo::new);
    }
}
