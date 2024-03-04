package javafx;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static javafx.beans.binding.Bindings.createBooleanBinding;
import static javafx.beans.binding.Bindings.lessThanOrEqual;

public class BindingDemo extends Application {

//    public void start(Stage stage) {
//        TextArea shipping = new TextArea();
//        TextArea billing = new TextArea();
//        billing.textProperty().bindBidirectional(
//                shipping.textProperty());
//        VBox root = new VBox(new Label("Shipping"), shipping,
//                new Label("Billing"), billing);
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }


//    public void start(Stage stage) {
//        Circle circle = new Circle(100, 100, 100);
//        circle.setFill(Color.RED);
//        Pane pane = new Pane(circle);
//        Scene scene = new Scene(pane);
//        circle.centerXProperty().bind(
//                Bindings.divide(scene.widthProperty(), 2));
//        circle.centerYProperty().bind(
//                Bindings.divide(scene.heightProperty(), 2));
//        stage.setScene(scene);
//        stage.show();
//    }


    public void start(Stage stage) {
        Button smaller = new Button("Smaller");
        Button larger = new Button("Larger");
        Rectangle gauge = new Rectangle(0, 5, 50, 15);
        Rectangle outline = new Rectangle(0, 5, 100, 15);
        outline.setFill(null);
        outline.setStroke(Color.BLACK);
        Pane pane = new Pane(gauge, outline);
        smaller.setOnAction(
                event -> gauge.setWidth(gauge.getWidth() - 10));
        larger.setOnAction(
                event -> gauge.setWidth(gauge.getWidth() + 10));

         //применение метода из класса Bindings

         smaller.disableProperty().bind(
                lessThanOrEqual(gauge.widthProperty(), 0));
        //создание привязки из лямбда-выражения

        larger.disableProperty().bind(
                createBooleanBinding(
                         () -> gauge.getWidth() >= 100,
                 //это выражение вычисляется ...
         gauge.widthProperty())); // ... когда
        //изменяется данное свойство

         Scene scene = new Scene(new HBox(10, smaller,
                pane, larger));
         stage.setScene(scene);
         stage.show();
    }
}
