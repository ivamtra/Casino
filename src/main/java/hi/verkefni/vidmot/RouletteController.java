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




    public int spin() throws InterruptedException {
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


    //--------------------- Initializer --------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Upphafsstillir isRed fylkið
        setIsRed();
        setHashMap();
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
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu-view.fxml"));
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
                    numberLandedOn = getnumber();
                    System.out.println(numberLandedOn);

                    // Rauður bettaður
                    if (isRed[numberMap.get(numberLandedOn)]) {
                        winner();
                    }
                    else
                        loser();
                }
        );
    }

    public void betOnBlack(ActionEvent event) throws InterruptedException {
        vedmalScaler = 2;
        spin();

        timeline.setOnFinished(e -> {
            numberLandedOn = getnumber();

            // Svartur bettaður

            // Ekki rauður
            if (!isRed[numberMap.get(numberLandedOn)]) {
                // Grænn
                if (numberLandedOn == -1  || numberLandedOn == 0)
                    loser();
                else
                    winner();
            }
            else
                loser();

            System.out.println(numberLandedOn);

        });
    }

    public void betOnGreen(ActionEvent event) throws InterruptedException {
        vedmalScaler = 18;
        spin();
        timeline.setOnFinished(e -> {
            numberLandedOn = getnumber();
            if (numberLandedOn == -1 || numberLandedOn == 0) {
                    winner();
                }
                else
                    loser();
                System.out.println(numberLandedOn);
        });
    }

    public void betOnOdds(ActionEvent event) throws InterruptedException {
        vedmalScaler = 2;
        spin();
        timeline.setOnFinished(e -> {
            numberLandedOn = getnumber();


            if (numberLandedOn % 2 == 1)
                winner();
            else
                loser();
            System.out.println(numberLandedOn);

        });
    }

    public void betOnEvens(ActionEvent event) throws InterruptedException {
        vedmalScaler = 2;
        spin();
        timeline.setOnFinished(e -> {
            numberLandedOn = getnumber();


            if (numberLandedOn % 2 == 1)
                loser();
            else
                winner();
            System.out.println(numberLandedOn);

        });
    }

    public void betOnNumber(ActionEvent event) throws InterruptedException {
        vedmalScaler = 36;
        spin();

        timeline.setOnFinished(e -> {
            numberLandedOn = getnumber();
            if (numberLandedOn == Integer.parseInt(fxTextField.getText()))
                winner();
            else
                loser();
            System.out.println(numberLandedOn);

        });

    }
    







    //--------------- Vinnsluföll -----------------------------

    // Hægt að bæta við isBlack(), isRed() o.s.frv.


    public void setIsRed() {
        isRed = new boolean[38];
        for (int i = 0; i < 38; i++) {
            if (i % 2 == 0) {
                isRed[i] = false;
            }
            else
                isRed[i] = true;
        }
        // Grænn
        isRed[19] = false;
    }

    public void setHashMap() {
        numberMap = new HashMap<>();
        for (int i = 0; i < 38; i++) {
            numberMap.put(numbers[i], i);
        }
    }

    public void winner()  {
        System.out.println("Winner");
        Peningur.PENINGUR += vedmal*vedmalScaler;
        System.out.println(Peningur.PENINGUR);
        fxMoneyText.setText("" + Peningur.PENINGUR);

        fxWinText.setText("Þú vannst " + vedmal*vedmalScaler + "kr!");
        fxWinText.setVisible(true);

        KeyFrame winnerKeyframe = new KeyFrame(Duration.millis(2000),
                e -> {
                    fxWinText.setVisible(true);
                });

        Timeline showTextTimeline = new Timeline(winnerKeyframe);

        showTextTimeline.setCycleCount(1);
        showTextTimeline.play();
        showTextTimeline.setOnFinished(e -> {
            fxWinText.setVisible(false);
        });
    }

    public void loser() {
        System.out.println("Loser");
        //Peningur.PENINGUR -= vedmal;
        //System.out.println(Peningur.PENINGUR);
        //fxMoneyText.setText("" + Peningur.PENINGUR);
    }

    // Gæti farið í vinnslu með fxRouletteImage sem argument
    public int getnumber() {
        double angle = fxRouletteImage.getRotate() % 360;

        double angleOfNumber = 360 - angle;

        double offSet = -2.6;
        double step = 360/38.0; // Gradur sem hver tala tekur

        int iterator = 0;

        while(iterator*step - offSet < angleOfNumber) {
            iterator++;
        }
        try {
            return numbers[iterator];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return  -1;
        }
    }


}
