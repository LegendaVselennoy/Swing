package swing;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class SimpleTree {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            var frame=new SimpleTreeFrame();
            frame.setTitle("SimpleTree");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 *  Этот фрейм содержит простое дерево, отображающее построенную вручную модель дерева
 */
class SimpleTreeFrame extends JFrame{
    private static final int DEFAULT_WIDTH=300;
    private static final int DEFAULT_HEIGHT=200;

    public SimpleTreeFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        // подготовить данные для модели дерева
        var root=new DefaultMutableTreeNode("World");
        var country=new DefaultMutableTreeNode("USA");
        root.add(country);
        var state=new DefaultMutableTreeNode("California");
        country.add(state);
        var city=new DefaultMutableTreeNode("San Jose");
        state.add(city);
        city=new DefaultMutableTreeNode("Cupertino");
        state.add(city);
        state=new DefaultMutableTreeNode("Michigan");
        country.add(state);
        city=new DefaultMutableTreeNode("Ann Arbor");
        state.add(city);
        country=new DefaultMutableTreeNode("Germany");
        root.add(country);
        state=new DefaultMutableTreeNode("Schleswig-Holstein");
        country.add(state);
        city=new DefaultMutableTreeNode("Kiel");
        state.add(city);

        // построить дерево и разместить его на прокручиваемой панели
        JTree tree=new JTree(root);
        add(new JScrollPane(tree));
    }
}
