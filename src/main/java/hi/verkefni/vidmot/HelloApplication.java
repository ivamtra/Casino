package hi.verkefni.vidmot;

import hi.verkefni.vinnnsla.Peningur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMenu-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        Parent root = FXMLLoader.load(getClass().getResource("mainMenu-view.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Casino");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        Peningur.PENINGUR = 5000;
        launch();
    }
}