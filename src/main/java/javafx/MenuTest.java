package javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuTest extends Application {

    private TextArea textArea=new TextArea();

    /**
     * Вынуждает данный пункт меню или порождённые от меню пункты выполнять действие по умолчанию,
     * если конкретное действие ещё не определено
     * @param item Пункт меню(или же само меню)
     * @param action Действие по умолчанию
     */
    private void defaultAction(MenuItem item, EventHandler<ActionEvent>action){
        if (item instanceof Menu)
            for (MenuItem child:((Menu) item).getItems())
                defaultAction(child,action);
        else if (item.getOnAction()==null)
            item.setOnAction(action);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Menu fileMenu=new Menu("File");
        MenuItem exitItem=new MenuItem("Exit");
        exitItem.setOnAction(event -> Platform.exit());
        //продемонстрировать оперативные клавиши
        MenuItem newItem=new MenuItem("New");
        MenuItem openItem=new MenuItem("Open...");
        openItem.setAccelerator(KeyCombination.keyCombination("Shortcut+O"));
        MenuItem saveItem=new MenuItem("Save");
        saveItem.setAccelerator(KeyCombination.keyCombination("Shortcut+S"));
        MenuItem saveAsItem=new MenuItem("Save as...");
        fileMenu.getItems().addAll(
                newItem,
                openItem,
                saveItem,
                saveAsItem,
                new SeparatorMenuItem(),
                exitItem
        );
        //продемонстрировать пункты меню, помеченные флажками и кнопками переключателями
        CheckMenuItem readOnlyItem=new CheckMenuItem("Read-only");
        readOnlyItem.setOnAction(event -> {
            saveItem.setDisable(readOnlyItem.isSelected());
            saveAsItem.setDisable(readOnlyItem.isSelected());
        });

        /*
        С другой стороны, воспользоваться привязкой:
        saveItem.disableProperty().bind(readOnlyItem.disableProperty());
        saveAsItem.disableProperty().bind(readOnlyItem.selectedProperty());
        */

        ToggleGroup group=new ToggleGroup();
        RadioMenuItem insertItem=new RadioMenuItem("Insert");
        insertItem.setToggleGroup(group);
        insertItem.setSelected(true);
        RadioMenuItem overTypeItem=new RadioMenuItem("Overtype");
        overTypeItem.setToggleGroup(group);

        Menu editMenu=new Menu("Edit");
        //продемонстрировать пиктограммы
//        MenuItem cutItem=new MenuItem("Cut",new ImageView("menu/cut.gif"));
//        MenuItem copyItem=new MenuItem("Copy",new ImageView("menu/copy.gif"));
//        MenuItem pasteItem=new MenuItem("Paste",new ImageView("menu/paste.gif"));
        //продемонстрировать контекстное меню
//        ContextMenu contextMenu=new ContextMenu(cutItem,copyItem,pasteItem);
//        textArea.setContextMenu(contextMenu);
//        editMenu.getItems().addAll(cutItem,copyItem,pasteItem);
        //продемонстрировать вложенные меню
//        Menu optionsMenu=new Menu("Options",new ImageView("menu/options.gif"),
//                readOnlyItem,insertItem,overTypeItem);
//        editMenu.getItems().add(optionsMenu);
        //продемонстрировать клавиши быстрого выбора
        MenuItem aboutProgramItem=new MenuItem("About this program");
        MenuItem aboutCoreJavaItem=new MenuItem("About Core Java");
        Menu helpMenu=new Menu("Help",null,aboutProgramItem,aboutCoreJavaItem);
        //ввести строку меню
        MenuBar bar=new MenuBar(fileMenu,editMenu,helpMenu);
        VBox root=new VBox(bar,textArea);
        for (Menu menu:bar.getMenus())
            defaultAction(menu,event -> {
                MenuItem item=(MenuItem) event.getSource();
                textArea.appendText(item.getText()+" selected\n");
            });
        stage.setScene(new Scene(root));
        stage.setTitle("Menu");
        stage.show();
    }
}
