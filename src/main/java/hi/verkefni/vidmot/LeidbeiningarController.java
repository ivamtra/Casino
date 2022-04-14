package hi.verkefni.vidmot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LeidbeiningarController implements Initializable {


    @FXML
    private Label fxLabel;

    @FXML
    private ScrollPane fxScrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fxScrollPane.setPannable(true);

        fxLabel.setText("Leiðbeiningar\n" +
                "\n" +
                "--------------------------------------------------------------------------------------------\n" +
                "\n" +
                "Blackjack\n" +
                "\n" +
                "Hvert spil í blackjack hefur gildi frá 1 - 11. Öll mannspilin hafa gildið 10,\n" +
                "ás er 11 og öll hin spilin hafa gildið sem stendur á spilinu.\n" +
                "Markmið leiksins er að fá hærri hönd heldur en dealer en án þess að fara yfir 21.\n" +
                "\n" +
                "Ef leikmaður fer yfir 21 sprengur hann og tapar veðmálinu á þeirri hönd.\n" +
                "Leikmaður hefur 3 hendur sem hægt er að veðja á.\n" +
                "Leikmaður vinnur veðmál ef hann fær hærra en dealer án þess að springa\n" +
                "eða ef dealer springur.\n" +
                "\n" +
                "\n" +
                "Nýr leikur takkinn byrjar nýjan leik með sömu upphæðir og í leiknum á undan.\n" +
                "\n" +
                "Nýtt veðmál takkinn byrjar nýjan leik með nýjum upphæðum.\n" +
                "\n" +
                "Hit takkinn gefur leikmanni eitt nýtt spil sem örin bendir á, leikmaður getur haldið áfram að\n" +
                "að fá fleiri spil með því að ýta á hit takkann aftur en getur í mesta lagi verið með 5 spil á\n" +
                "hönd.\n" +
                "\n" +
                "Stand takkinn er þegar leikmaður vill ekki fá fleiri spil á höndina sem örin bendir á.\n" +
                "Þá heldur leikurinn áfram á næstu hönd. Ef leikmaður er búinn með allar hendurnar klárast\n" +
                "leikurinn og leikmaður fær greidd öll veðmál sem hann vinnur.\n" +
                "\n" +
                "Double takkinn tvöfaldar veðmálið á höndinni og gefur eitt nýtt spil.\n" +
                "Ekki er hægt að fá fleiri spil á höndinni sem var tvöfölduð.\n" +
                "\n" +
                "--------------------------------------------------------------------------------------------\n" +
                "\n" +
                "Rúletta\n" +
                "\n" +
                "Í rúlettu getur leikmaður valið um mörg mismunandi veðmál og vinnur ef rúlettan lendir á tölu\n" +
                "sem samsvarar veðmálinu.\n" +
                "\n" +
                "Ef notandi veðjar á rauðan, svartan, slétta tölu eða oddatölu tvöfaldar leikmaður upphæðina\n" +
                "sína ef hann vinnur.\n" +
                "\n" +
                "Ef notandi veðjar á grænan 18-faldar hann veðmál sitt ef hann vinnur.\n" +
                "\n" +
                "Ef notandi veðjar á tölu af eigin vali 36-faldar hann veðmál sitt ef hann vinnur.\n");
    }

    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("NewMainMenu-view.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
