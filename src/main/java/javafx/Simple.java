package javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Simple extends Application {
    private static final int MESSAGE_X=75;
    private static final int MESSAGE_Y=100;
    private static final int PREFERRED_WIDTH=300;
    private static final int PREFERRED_HEIGHT=200;


    public void start(Stage stage) throws IOException {
        Text message=new Text(MESSAGE_X,MESSAGE_Y,"You're the best");
        Pane root=new Pane(message);
        root.setPrefSize(PREFERRED_WIDTH,PREFERRED_HEIGHT);
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Cool");
        stage.show();
    }
}
