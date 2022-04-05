package hi.verkefni.vidmot;

import hi.verkefni.vinnnsla.Peningur;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class RouletteController implements Initializable {

    //TODO hægt að setja getNumber í sér klasa

    @FXML
    private Button fxSpinButton;

    @FXML
    private ImageView fxRouletteImage;

    private Timeline timeline;

    @FXML
    private Pane fxPane;


    // Offset = -2.6 gradur
    // Þ.e. 00 er i -2.6 gradum

    // -1 taknar 00
    private int[] numbers = {-1, 27, 10, 35, 29, 12, 8, 19, 31, 18, 6, 21, 33, 16, 4, 23, 35, 14, 2, 0, 28, 9, 26, 30, 11, 7, 20, 32, 17, 5, 22, 34, 15, 3, 24, 36, 13, 1};


    public void spin() throws InterruptedException {

        // Horn eru í gráðum
        double doubleInitialVelocity = Math.round((Math.random()*10.0 + 5)*100.0)/100.0;

        //Math.round(x*100.0)/100.0 gefur 2 aukastafi

        AtomicReference<Double> initialVelocity = new AtomicReference<>(doubleInitialVelocity); // gradur/sek
        KeyFrame keyFrame = new KeyFrame(Duration.millis(10),
                e -> {
                    fxRouletteImage.setRotate(fxRouletteImage.getRotate() + initialVelocity.get());
                    initialVelocity.updateAndGet(v -> new Double((double) (v - 0.01)));
                });

        System.out.println(initialVelocity);

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount((int) doubleInitialVelocity*100);
        timeline.play();

        timeline.setOnFinished(e ->
                {
                    System.out.println(getnumber());
                }
                );
        Peningur.PENINGUR -= 500;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fxRouletteImage.setRotate(Math.random()*360);
        System.out.println(fxRouletteImage.getRotate());

        System.out.println(getnumber());

        Polygon triangle = new Polygon();

        double initialX = 175.0;
        double initialY = 24.0;
        double length = 10;
        triangle.getPoints().setAll(
                initialX-length, initialY,
                initialX+length, initialY,
                initialX, initialY+length
        );
        triangle.setFill(Color.BLUE);
        fxPane.getChildren().add(triangle);

    }

    public int getnumber() {
        double angle = fxRouletteImage.getRotate() % 360;

        double angleOfNumber = 360 - angle;

        double offSet = -2.6;
        double step = 360/38.0; // Gradur sem hver tala tekur

        int iterator = 0;

        while(iterator*step - offSet < angleOfNumber) {
            iterator++;
        }
        return numbers[iterator];
    }




    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu-view.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
