package javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class EventTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Button button=new Button("Random font");
        Text message=new Text("Times New Roman");
        message.setFont(Font.font("Gloucester MT Extra Condensed",100));
        List<String> families=Font.getFamilies();
        Random generator=new Random();
        button.setOnAction(event->{
            String newFamily=families.get(
                    generator.nextInt(families.size()));
                    message.setText(newFamily);
                    message.setFont(Font.font(
                            newFamily,message.getFont().getSize()));
        });
        Slider slider=new Slider();
        slider.setValue(100);
        slider.valueProperty().addListener(property->{
            double newSize=slider.getValue();
            message.setFont(Font.font(
                    message.getFont().getFamily(),newSize));
        });
        VBox root=new VBox(button,slider,message);
        Scene scene=new Scene(root);
        stage.setTitle("Event");
        stage.setScene(scene);
        stage.setOnCloseRequest(event->{
            if (slider.getValue()==100){
                event.consume();//препятствует закрытию окна
                Alert alert=new Alert(Alert.AlertType.INFORMATION,"Move the slider before quitting.");
                alert.showAndWait();
            }
        });
        stage.show();
    }
}
