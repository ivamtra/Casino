package hi.verkefni.vidmot;

import hi.verkefni.vinnnsla.Peningur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    /******************************************************************************
     *  Nafn    : Ívan Már Þrastarson
     *
     *  T-póstur: imt1@hi.is
     *
     *
     *  Lýsing  : Viðmótsklasi sem stjórnar öllu sem tengist aðalvalmyndinni.
     *            Hér getur notandi valið hvaða leik hann vill spila.
     *
     *
     *****************************************************************************/

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private HBox fxHbox;


    @FXML
    private ImageView fxRouletteMenuIcon;

    @FXML
    private ImageView fxBlackjackMenuIcon;

    @FXML
    private Text fxMoneyText;

    @FXML
    private Button fxLeidbeingaButton;

    /**
     * Handler fyrir þegar notandi vill spila blackjack.
     * Notandinn ýtir á blackjack myndina og nýr leikur
     * hefst.
     * @param event
     * @throws IOException
     */
    @FXML
    public void BlackjackIconHandler(MouseEvent event) throws IOException {
        System.out.println("Blackjack Icon clicked");

        root = FXMLLoader.load(getClass().getResource("newBlackjack-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }


    /**
     * Handler fyrir rúlettuvalmyndina. Notandi ýtir
     * á rúlettumyndina og nýr rúlettuleikur hefst.
     * @param event
     * @throws IOException
     */
    @FXML
    public void rouletteIconHandler(MouseEvent event) throws IOException {
        System.out.println("Roulette Icon clicked");

        root = FXMLLoader.load(getClass().getResource("roulette-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initializer fyrir aðalvalmyndina.
     * Fallið uppfærir hversu mikinn pening notandinn
     * hefur og birtir það neðst á skjánum.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(Peningur.PENINGUR);
        fxMoneyText.setText("" + Peningur.PENINGUR);
    }

    public void leidbeiningarHandler(ActionEvent event) throws IOException {
        System.out.println("Leiðbeiningatakki ýttur");

        root = FXMLLoader.load(getClass().getResource("leidbeininga-view.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void leidbeingarHover(MouseEvent mouseEvent) {
        System.out.println("Hoverað yfir");
        fxLeidbeingaButton.setStyle("-fx-background-color: #d3d3d3");

    }

    public void paneHovered(MouseEvent mouseEvent) {
        fxLeidbeingaButton.setStyle("-fx-background-color: #ffffff");
    }
}