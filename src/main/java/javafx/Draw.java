package javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Draw extends Application {
    private static final int PREFERRED_WIDTH=400;
    private static final int PREFERRED_HEIGHT=400;

    public void start(Stage stage){
        double leftX=100;
        double topY=100;
        double width=200;
        double height=150;

        Rectangle rect=new Rectangle(leftX,topY,width,height);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.BLACK);

        //эллипс, вписанный в прямоугольник
        double centerX=leftX+width/2;
        double centerY=topY+height/2;
        Ellipse ellipse=new Ellipse(centerX,centerY,width/2,height/2);
        ellipse.setFill(Color.PEACHPUFF);

        //диагональная линия
        Line diagonal=new Line(leftX,topY,leftX+width,topY+height);

        //Окружность с таким же центром, как и у эллипса
        double radius=150;
        Circle circle=new Circle(centerX,centerY,radius);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.RED);
        Pane root=new Pane(rect,ellipse,diagonal,circle);
        root.setPrefSize(PREFERRED_WIDTH,PREFERRED_HEIGHT);
        stage.setScene(new Scene(root));
        stage.setTitle("Draw");
        stage.show();
    }
}
