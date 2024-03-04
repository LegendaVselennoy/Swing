package javafx;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDemo extends Application implements Initializable {
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Button okButton;
    @FXML private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        okButton.setOnAction(event -> {
            Alert alert=new Alert(Alert.AlertType.INFORMATION,"Veryfying "+username.getText()+":"+password.getText());
            alert.showAndWait();
        });
        cancelButton.setOnAction(event -> {
            username.setText("");
            password.setText("");
        });
    }

    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent root= FXMLLoader.load(
                    getClass().getResource("demo.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("FXML");
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
