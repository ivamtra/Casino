package hi.verkefni.vidmot;

import hi.verkefni.vinnnsla.Peningur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/******************************************************************************
 *  Nafn    : Ívan Már Þrastarson
 *
 *  T-póstur: imt1@hi.is
 *
 *
 *  Lýsing  : Viðmótsklasi sem ræsir forritið og upphafsstillir pening
 *  leikmanns.
 *
 *
 *****************************************************************************/

public class CasinoApplication extends Application {

    /**
     * Start fall fyrir forritið.
     * Ræsir Casino forritinu.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CasinoApplication.class.getResource("newMainMenu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("Casino");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Main fall sem kallar á start fallið
     * og upphafsstillir pening leikmanns.
     * @param args
     */
    public static void main(String[] args) {
        Peningur.PENINGUR = 5000;
        launch();
    }
}