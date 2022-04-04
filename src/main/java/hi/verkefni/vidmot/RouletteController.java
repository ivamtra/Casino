package hi.verkefni.vidmot;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
public class RouletteController {

    @FXML
    private Button fxSpinButton;

    @FXML
    private ImageView fxRouletteImage;


    public void spin() throws InterruptedException {


        double initialVelocity = Math.random()*10.0;

        while (initialVelocity > 0.0) {
            System.out.println(initialVelocity);
            fxRouletteImage.setRotate(fxRouletteImage.getRotate() + initialVelocity);
            initialVelocity -= 0.002;
            Thread.sleep(20);
        }
    }
}
