package hi.verkefni.vidmot;

import hi.verkefni.vinnnsla.Peningur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private HBox fxHbox;


    @FXML
    private ImageView fxRouletteMenuIcon;

    @FXML
    private ImageView fxBlackjackMenuIcon;

    private HelloController helloController;

    @FXML
    public void rouletteIconHandler() {

    }



    // Virkar
    @FXML
    public void BlackjackIconHandler(MouseEvent event) throws IOException {
        System.out.println("Blackjack Icon clicked");

        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    public void buttonHandler(ActionEvent e) throws IOException {
        System.out.println("Money lost");
        Peningur.PENINGUR -= 500;
        System.out.println(Peningur.PENINGUR);

    }

    @FXML
    public void rouletteIconHandler(MouseEvent event) throws IOException {
        System.out.println("Roulette Icon clicked");

        root = FXMLLoader.load(getClass().getResource("roulette-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(Peningur.PENINGUR);
    }
}