package javafx;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TextControlTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        final double rem=new Text("").getLayoutBounds().getHeight();
        GridPane pane=new GridPane();
        pane.setHgap(0.8*rem);
        pane.setVgap(0.8*rem);
        pane.setPadding(new Insets(0.8*rem));

        Label usernameLabel=new Label("User name:");
        Label passwordLabel=new Label("Password:");

        TextField username=new TextField();
        username.setPromptText("Choose a user name");
        PasswordField password=new PasswordField();
        password.setPromptText("Choose a password");
        TextArea textArea=new TextArea();
        textArea.setPrefRowCount(10);
        textArea.setPrefColumnCount(20);
        textArea.setWrapText(true);
        textArea.setEditable(false);

        Button okButton=new Button("Ok");
        okButton.setOnAction(event ->
            textArea.appendText(
                    "User name: "+username.getText()
                    +"\nPassword: "+password.getText()+"\n"));
        pane.add(usernameLabel,0,0);
        pane.add(username,1,0);
        pane.add(passwordLabel,0,1);
        pane.add(password,1,1);
        pane.add(textArea,0,2,2,1);
        pane.add(okButton,0,3,2,1);

        GridPane.setHalignment(usernameLabel, HPos.RIGHT);
        GridPane.setHalignment(passwordLabel, HPos.RIGHT);
        GridPane.setHalignment(okButton, HPos.CENTER);

        stage.setScene(new Scene(pane));
        stage.setTitle("TextControl");
        stage.show();
    }
}
