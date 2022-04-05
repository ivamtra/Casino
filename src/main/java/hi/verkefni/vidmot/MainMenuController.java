package hi.verkefni.vidmot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

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

        root = FXMLLoader.load(getClass().getResource("blackjack-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


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
}