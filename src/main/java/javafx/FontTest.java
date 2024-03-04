package javafx;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FontTest extends Application {
    private static final int PREFERRED_WIDTH=400;
    private static final int PREFERRED_HEIGHT=400;

    @Override
    public void start(Stage stage) throws Exception {
      //сконструировать текстовое сообщение в точке с координатами(0,0)
        Text message=new Text("Bonjour le monde!");
//        Font f=Font.font("Lucida Bright", Font.BOLD,36);
//        message.setFont(f);
        //получить размеры изображения
        Bounds messageBounds=message.getBoundsInParent();
        double ascent=-messageBounds.getMinY();
        double descent=messageBounds.getMaxY();
        double width=messageBounds.getWidth();
        //отцентровать сообщение по горизонтали
        double baseY=100;
        double topY=baseY-ascent;
        double leftX=(PREFERRED_WIDTH-width)/2;
        message.relocate(leftX,topY);
        //сконструировать ограничивающий прямоугольник и базовую линию
        Rectangle rect=new Rectangle(leftX,topY,width,ascent+descent);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.GRAY);
        Line baseline=new Line(leftX,baseY,leftX+width,baseY);
        baseline.setStroke(Color.GRAY);
//        отцентровать изображение прямо под сообщением
        ImageView image=new ImageView("image/world.png");
        Bounds imageBounds=image.getBoundsInParent();
        image.relocate((PREFERRED_WIDTH-imageBounds.getWidth())/2,baseY+descent);

        Pane root=new Pane(message,rect,baseline,image);
//        Pane root=new Pane(message,rect,baseline);
        root.setPrefSize(PREFERRED_WIDTH,PREFERRED_HEIGHT);
        stage.setScene(new Scene(root));
        stage.setTitle("Front");
        stage.show();
    }
}
