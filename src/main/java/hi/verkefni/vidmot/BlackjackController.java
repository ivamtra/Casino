package hi.verkefni.vidmot;

import hi.verkefni.vinnnsla.Leikmadur;
import hi.verkefni.vinnsla.SpilV;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import hi.verkefni.vinnsla.Stokkur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import hi.verkefni.vinnnsla.Peningur;


/******************************************************************************
 *  Nafn    : Ívan Már Þrastarson
 *  T-póstur: imt1@hi.is
 *
 *  Lýsing  : Viðmótsklasi sem frumstillir bingóspjaldið og höndlar
 *            notendaatburði.
 *
 *
 *****************************************************************************/

public class BlackjackController implements Initializable {

    private Stokkur stokkur = new Stokkur();
    private final Leikmadur leikmadur = new Leikmadur();
    private final Leikmadur dealer = new Leikmadur();

    // Dealerinn hefur alltaf eitt falið spil í venjulegum blackjack.
    // Mig langaði að hafa það í þessu forriti
    // þannig ég nota þessa tilviksbreytu til að útfæra það.
    private SpilV dealerSpilFalid;

    @FXML
    private Text fxNafnDealer, fxNafnLeikmanns;

    @FXML
    private HBox leikMadurHbox, dealerHbox;

    @FXML
    private Button fxHitButton, fxStandButton;





    /**
     * Gefur leikmanni nýtt spil.
     * Hægt er að hafa í mesta lagi 5 spil
     * og leikmaður springur ef samtalan hans
     * fer yfir 21.
     *
     */
    @FXML
    public void nyttSpilHandler() {
        SpilV s = stokkur.dragaSpil();
        leikmadur.gefaSpil(s);


        Spil spil = new Spil();
        spil.setSpil(s);
        leikMadurHbox.getChildren().add(spil);

        if (leikmadur.getSamtals() > 21) {
            Spil falidSpil = new Spil();
            falidSpil.setSpil(dealerSpilFalid);
            dealerHbox.getChildren().add(falidSpil);

            fxNafnDealer.setText("Dealer vann með samtölu " + dealer.getSamtals());
            fxHitButton.setDisable(true);
            fxStandButton.setDisable(true);


        }
        if (leikmadur.getFjoldiSpila() >= 5) {
            System.out.println("Of mörg spil");
            komidNogHandler();
            return;
        }

        fxNafnLeikmanns.setText(leikmadur.getNafn() + " samtala " +  leikmadur.getSamtals());
    }

    /**
     * Handler fyrir þegar leikmaður vill
     * ekki fleiri spil. Dealer dregur þá
     * spil þangað til hann er kominn yfir 17.
     * Ef leikmaður er með hærri samtölu en dealer
     * eða dealer springur þá vinnur leikmaður.
     *
     */
    @FXML
    public void komidNogHandler() {
        Spil dealerVidmotsSpilFalid = new Spil();
        dealerVidmotsSpilFalid.setSpil(dealerSpilFalid);
        dealerHbox.getChildren().add(dealerVidmotsSpilFalid);

        while(dealer.getSamtals() < 17 && dealer.getFjoldiSpila() < 5) {
            SpilV s = stokkur.dragaSpil();
            dealer.gefaSpil(s);
            Spil synilegtSpil = new Spil();
            synilegtSpil.setSpil(s);
            dealerHbox.getChildren().add(synilegtSpil);

        }
        if (!leikmadur.vinnurDealer(dealer)) {
            fxNafnDealer.setText("Dealer vann með samtölu " + dealer.getSamtals());
        }
        else {
            fxNafnDealer.setText("Dealer tapar með samtölu " + dealer.getSamtals());
        }

        fxStandButton.setDisable(true);
        fxHitButton.setDisable(true);
    }


    /**
     *
     * Handler fyrir þegar leikmaður vill
     * hefja nýjan leik. Dealer fær 2
     * spil en eitt er falið. Leikmaður
     * fær 2 spil og ef samtala spilanna er
     * 21 þá vinnur leikmaður strax í byrjun.
     *
     */
    @FXML
    public void nyrLeikurHandler() {
        fxStandButton.setDisable(false);
        fxHitButton.setDisable(false);

        leikMadurHbox.getChildren().clear();

        dealerHbox.getChildren().clear();

        stokkur = new Stokkur();
        dealer.nyrLeikur();
        leikmadur.nyrLeikur();
        for (int i = 0; i < 2; i++) {
            SpilV leikMadurSpil = stokkur.dragaSpil();
            leikmadur.gefaSpil(leikMadurSpil);
            Spil vidmotsSpil = new Spil();
            vidmotsSpil.setSpil(leikMadurSpil);
            leikMadurHbox.getChildren().add(vidmotsSpil);
        }

        dealerSpilFalid = stokkur.dragaSpil();
        dealer.gefaSpil(dealerSpilFalid);

        SpilV dealerSpilSynilegt = stokkur.dragaSpil();
        dealer.gefaSpil(dealerSpilSynilegt);

        Spil dealerVidmotsSpil = new Spil();
        dealerVidmotsSpil.setSpil(dealerSpilSynilegt);
        dealerHbox.getChildren().add(dealerVidmotsSpil);

        // 21 í upphafi leiks
        if(leikmadur.getSamtals() == 21) {
            fxNafnLeikmanns.setText(leikmadur.getNafn() + " samtala " + leikmadur.getSamtals());
            fxNafnDealer.setText(leikmadur.getNafn() + " vann með Blackjack!");

            System.out.println("Blackjack");

            fxHitButton.setDisable(true);
            fxStandButton.setDisable(true);
            return;
        }

        fxNafnLeikmanns.setText(leikmadur.getNafn() + " samtala " + leikmadur.getSamtals());
        fxNafnDealer.setText("Dealer samtala " + dealerSpilSynilegt.getVirdi());
    }

    /**
     * Initialize aðferð í controllernum.
     * Býr til Text-dialog sem tekur inn nafn leikmanns
     * og upphafsstillir nýjan blackjack leik eftir
     * að tekið er inn nafn.
     *
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nyrLeikurHandler();
    }

    public void goBackHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu-view.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
