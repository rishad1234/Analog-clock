
package analogclock;

import java.util.Calendar;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class AnalogClock extends Application {
    
    private double posX;
    private double posY;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Group rootGroup = new Group();
        
        rootGroup.setOnMousePressed((MouseEvent event) -> {
            posX = event.getSceneX();
            posY = event.getSceneY();
        });
        rootGroup.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - posX);
            primaryStage.setY(event.getScreenY() - posY);
        });
        
        rootGroup.setOnMouseClicked((MouseEvent event) ->{
            if(event.getClickCount() >= 2){
                System.exit(0);
            }
        });
        
        Scene scene = new Scene(rootGroup, 400, 300, Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        
        rootGroup.setEffect(new DropShadow(1, Color.BLACK));
        primaryStage.setX(Screen.getPrimary().getBounds().getWidth() - 400);
        primaryStage.setY(0);
        
        primaryStage.getIcons().add(new Image("/image/icon.png"));
        
        launchClock(rootGroup);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void launchClock(Group root){
        Line line[] = new Line[60];
        Color bloodRed =  Color.rgb(131, 3, 3);
        final Arc circleHours = new Arc(203, 150, 53, 53, 90, 360);
        final Arc circleMin = new Arc(203, 150, 103, 103, 90, 360);
        final Arc circleSec = new Arc(203, 150, 133, 133, 90, 360);
        final Arc middleCircle = new Arc(203, 150, 40, 40, 0, 360);
        root.getChildren().addAll(circleSec, circleMin, circleHours, middleCircle); 
        
        circleSec.setFill(Color.TRANSPARENT);
        circleMin.setFill(Color.TRANSPARENT);
        circleHours.setFill(Color.TRANSPARENT);
        middleCircle.setFill(Color.TRANSPARENT);
        
        circleSec.setStroke(bloodRed);
        circleMin.setStroke(Color.DARKGREEN);
        circleHours.setStroke(bloodRed);
        middleCircle.setStroke(Color.MEDIUMTURQUOISE);
        
        circleHours.setStrokeWidth(3);
        circleMin.setStrokeWidth(3);
        circleSec.setStrokeWidth(3);
        middleCircle.setStrokeWidth(10);
        
        circleHours.setStrokeLineCap(StrokeLineCap.SQUARE);
        circleMin.setStrokeLineCap(StrokeLineCap.SQUARE);
        circleSec.setStrokeLineCap(StrokeLineCap.SQUARE);
        middleCircle.setStrokeLineCap(StrokeLineCap.SQUARE);
        
        Group group = new Group();
        for (double s = 1.0; s <= 60.0; s = s + 1.0) {
            if (s % 5 == 0) {
                line[(int) s - 1] = new Line(
                        203.0 + (124.0 * Math.cos((s * Math.PI) / 30.0)),
                        150.0 + (124.0 * Math.sin((s * Math.PI) / 30.0)),
                        203.0 + (110.0 * Math.cos((s * Math.PI) / 30.0)),
                        150.0 + (110.0 * Math.sin((s * Math.PI) / 30.0))
                );
                System.out.println(203.0 + 124.0 * Math.cos(s * Math.PI / 30.0));
                System.out.println(150.0 + 124.0 * Math.sin(s * Math.PI / 30.0));
                System.out.println(203.0 + 110.0 * Math.cos(s * Math.PI / 30.0));
                System.out.println(150.0 + 110.0 * Math.sin(s * Math.PI / 30.0));
            } else {
                line[(int) s - 1] = new Line(
                        203.0 + (124.0 * Math.cos(s * Math.PI / 30.0)),
                        150.0 + (124.0 * Math.sin(s * Math.PI / 30.0)),
                        203.0 + (120.0 * Math.cos(s * Math.PI / 30.0)),
                        150.0 + (120.0 * Math.sin(s * Math.PI / 30.0))
                );
            }

            line[(int) s - 1].setStroke(Color.MEDIUMTURQUOISE);
            line[(int) s - 1].setStrokeWidth(4);
            line[(int) s - 1].setStrokeLineCap(StrokeLineCap.ROUND);
            
            group.getChildren().add(line[(int) s - 1]);
        }
        
        root.getChildren().add(group);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Calendar c = Calendar.getInstance();
                circleSec.setLength(-(c.get(Calendar.SECOND)) / 60.0 * 360.0);
                circleMin.setLength(-(c.get(Calendar.MINUTE)) / 60.0 * 360.0);
                circleHours.setLength(-c.get(Calendar.HOUR) / 12.0 * 360.0);
            }
        }));
        
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), middleCircle);
        fadeIn.setFromValue(1.0);
        fadeIn.setToValue(0.4);
        fadeIn.setCycleCount(Animation.INDEFINITE);
        fadeIn.setAutoReverse(true);
        fadeIn.play();
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
}
