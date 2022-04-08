package hi.verkefni.vidmot;

import hi.verkefni.vinnnsla.*;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static hi.verkefni.vinnnsla.RouletteVinnsla.*;

public class RouletteController implements Initializable {

    //TODO hægt að setja getNumber í sér klasa

    @FXML
    private Button fxSpinButton;

    @FXML
    private ImageView fxRouletteImage;

    private Timeline timeline;

    @FXML
    private Pane fxPane;

    private int numberLandedOn;

    @FXML
    private Text fxMoneyText, fxWinText;

    @FXML
    private TextField fxTextField, fxVedmal;

    @FXML
    private ChoiceBox<String> fxChoiceBox;

    private int vedmal;

    private int vedmalScaler = 1;


    // -1 taknar 00
    // index 0 og 19 eru grænir
    private int[] numbers = {-1, 27, 10, 35, 29, 12, 8, 19, 31, 18, 6, 21, 33, 16, 4, 23, 35, 14, 2, 0, 28, 9, 26, 30, 11, 7, 20, 32, 17, 5, 22, 34, 15, 3, 24, 36, 13, 1};
    private boolean[] isRed;
    private HashMap<Integer, Integer> numberMap;







    //--------------------- Initializer --------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Upphafsstillir isRed fylkið
        isRed = RouletteVinnsla.setIsRed();
        numberMap = RouletteVinnsla.setHashMap(numbers);
        fxMoneyText.setText("" + Peningur.PENINGUR);
        fxWinText.setVisible(false);



        for (int i = 0; i < 38; i++) {
            System.out.println(numberMap.get(i));
        }

        fxRouletteImage.setRotate(Math.random()*360);


        Polygon triangle = new Polygon();

        double initialX = 178.0; // var 175
        double initialY = 24.0;
        double length = 10;
        triangle.getPoints().setAll(
                initialX-length, initialY,
                initialX+length, initialY,
                initialX, initialY+20
        );
        triangle.setFill(Color.BLUE);
        fxPane.getChildren().add(triangle);

    }










    // ------------------------ Handlerar ---------------------------------

    @FXML
    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newMainMenu-view.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void betOnRed(ActionEvent event) throws InterruptedException {
        vedmalScaler = 2;
        spin();

        timeline.setOnFinished(e ->
                {
                    numberLandedOn = getnumber(fxRouletteImage, numbers);
                    System.out.println(numberLandedOn);

                    // Rauður bettaður
                    if (isRed[numberMap.get(numberLandedOn)]) {
                        winner(fxMoneyText, fxWinText, vedmal, vedmalScaler);
                    }
                    else
                        System.out.println("Loser");
                }
        );
    }

    public void betOnBlack(ActionEvent event) throws InterruptedException {
        vedmalScaler = 2;
        spin();

        timeline.setOnFinished(e -> {
            numberLandedOn = getnumber(fxRouletteImage, numbers);

            // Svartur bettaður

            // Ekki rauður
            if (!isRed[numberMap.get(numberLandedOn)]) {
                // Grænn
                if (numberLandedOn == -1  || numberLandedOn == 0)
                    System.out.println("Loser");
                else
                    winner(fxMoneyText, fxWinText, vedmal, vedmalScaler);
            }
            else
                System.out.println("Loser");

            System.out.println(numberLandedOn);

        });
    }

    public void betOnGreen(ActionEvent event) throws InterruptedException {
        vedmalScaler = 18;
        spin();
        timeline.setOnFinished(e -> {
            numberLandedOn = getnumber(fxRouletteImage, numbers);
            if (numberLandedOn == -1 || numberLandedOn == 0) {
                winner(fxMoneyText, fxWinText, vedmal, vedmalScaler);
                }
                else
                     System.out.println("Loser");
                System.out.println(numberLandedOn);
        });
    }

    public void betOnOdds(ActionEvent event) throws InterruptedException {
        vedmalScaler = 2;
        spin();
        timeline.setOnFinished(e -> {
            numberLandedOn = getnumber(fxRouletteImage, numbers);


            if (numberLandedOn % 2 == 1)
                winner(fxMoneyText, fxWinText, vedmal, vedmalScaler);
            else
                System.out.println("Loser");
            System.out.println(numberLandedOn);

        });
    }

    public void betOnEvens(ActionEvent event) throws InterruptedException {
        vedmalScaler = 2;
        spin();
        timeline.setOnFinished(e -> {
            numberLandedOn = getnumber(fxRouletteImage, numbers);


            if (numberLandedOn % 2 == 1)
                System.out.println("Loser");
            else
                winner(fxMoneyText, fxWinText, vedmal, vedmalScaler);
            System.out.println(numberLandedOn);

        });
    }

    public void betOnNumber(ActionEvent event) throws InterruptedException {
        vedmalScaler = 36;
        spin();


        timeline.setOnFinished(e -> {
            numberLandedOn = getnumber(fxRouletteImage, numbers);

            int numberBetted;

            // Jaðartilfelli þegar bettað er á 00 því það er geymt sem -1
            if (fxTextField.getText().equals("00"))
                numberBetted = -1;
            else
                numberBetted = Integer.parseInt(fxTextField.getText());

            if (numberLandedOn == numberBetted)
                winner(fxMoneyText, fxWinText, vedmal, vedmalScaler);
            else
                System.out.println("Loser");
            System.out.println(numberLandedOn);

        });

    }

    public int spin() throws InterruptedException {

        // arguments vedmal, fxVedmal, fxMoneyText, fxRouletteImage, timeline

        AtomicInteger x = new AtomicInteger();

        try {
            vedmal = Integer.parseInt(fxVedmal.getText());
        }
        catch (NumberFormatException ignored) {}

        Peningur.PENINGUR -= vedmal;

        fxMoneyText.setText("" + Peningur.PENINGUR);

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

        //System.out.println(x);
        return -100;
    }

    //--------------- Vinnsluföll -----------------------------


}
