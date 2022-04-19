package hi.verkefni.vidmot;

import hi.verkefni.vinnsla.Gildi;
import hi.verkefni.vinnsla.SpilV;
import hi.verkefni.vinnsla.Tegund;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;


/******************************************************************************
 *  Nafn    : Ívan Már Þrastarson
 *  T-póstur: imt1@hi.is
 *
 *  Lýsing  : Viðmótsklasi fyrir spil. Inniheldur útlitsupplýsingar um
 *            spil, hægt er að búa til nýtt tómt spil og gefa spilinu
 *            gildi og tegund með setSpil() aðferðinni.
 *
 *
 *****************************************************************************/

public class Spil extends AnchorPane {
    @FXML
    private ImageView efriMynd, nedriMynd;
    @FXML
    private Text efriTegund, nedriTegund;

    private final String[] myndir = {"hjarta", "spadi", "tigull", "lauf"};


    /**
     *
     * Constructor fyrir Spil sem
     * les inn viðmótið á spil-view.fxml
     * og upphafsstillir nýtt tómt spil.
     *
     */
    public Spil() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("spil-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException();
        }

    }


    /**
     * Tekur inn upplýsingar úr vinnsluspilinu spil
     * og gefur viðmótsspilinu gildi og tegund þess spils.
     *
     * @param spil vinnsluspil sem lesa á af
     */
    public void setSpil(SpilV spil) {
        Gildi gildi = spil.getGildi();
        Tegund tegund = spil.getTegund();

        // HJARTA, SPADI
        String tegundString = tegund.toString().toLowerCase();


        // K 9 4 A
        String gildiString = gildi.toString();

        efriTegund.setText(gildiString);
        nedriTegund.setText(gildiString);
        String imageNafn = tegundString + ".png";
        Image mynd = new Image(Objects.requireNonNull(Spil.class.getResourceAsStream(imageNafn)));
        efriMynd.setImage(mynd);
        nedriMynd.setImage(mynd);


    }



}
