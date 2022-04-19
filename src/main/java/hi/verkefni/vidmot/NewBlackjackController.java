package hi.verkefni.vidmot;

import hi.verkefni.vinnnsla.Leikmadur;
import hi.verkefni.vinnnsla.Peningur;
import hi.verkefni.vinnsla.SpilV;
import hi.verkefni.vinnsla.Stokkur;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static hi.verkefni.vinnnsla.BlackjackVinnsla.showText;
import static hi.verkefni.vinnnsla.Peningur.checkIfLegalBettingNumber;
import static hi.verkefni.vinnnsla.Peningur.illegalNumber;

/******************************************************************************
 *  Nafn    : Ívan Már Þrastarson
 *
 *  T-póstur: imt1@hi.is
 *
 *
 *  Lýsing  : Viðmótsklasi fyrir Blackjack. Inniheldur handlera sem stjórna
 *            veðmálum notandans. Hér hefur notandi þrjár hendur þar sem
 *            hann getur "hittað", "standað" og "doublað".
 *
 *
 *****************************************************************************/

public class NewBlackjackController implements Initializable {

    // ------------------------ Tilviksbreytur ------------------------------

    private Stokkur stokkur = new Stokkur();
    private final Leikmadur leikmadur1 = new Leikmadur();
    private final Leikmadur leikmadur2 = new Leikmadur();
    private final Leikmadur leikmadur3 = new Leikmadur();
    private final Leikmadur dealer = new Leikmadur();

    private final Leikmadur[] leikmenn = new Leikmadur[]{leikmadur1, leikmadur2, leikmadur3};

    // Dealerinn hefur alltaf eitt falið spil í venjulegum blackjack.
    // Mig langaði að hafa það í þessu forriti
    // þannig ég nota þessa tilviksbreytu til að útfæra það.
    private SpilV dealerSpilFalid;



    @FXML
    private HBox fxLeikMadurHbox1, fxLeikMadurHbox2, fxLeikMadurHbox3, fxDealerHbox;

    @FXML
    private final HBox[] leikmennHbox = new HBox[3];

    @FXML
    private Text fxHond1, fxHond2, fxHond3, fxHondDealers;
    private final Text[] fxHendur = new Text[3];

    @FXML
    private Button fxNyttVedmalButton, fxNyrLeikurButton, fxHitButton, fxStandButton, fxDoubleButton;

    @FXML
    private ImageView fxArrowImage;

    private int numerLeikmanns = 2; // Indexið á númer leikmanns

    @FXML
    private Text fxCashText, fxWinText;

    @FXML
    private TextField fxBet1, fxBet2, fxBet3;

    @FXML
    private final TextField[] fxBets = new TextField[3];

    private final boolean[] isBusted = new boolean[3];

    private final boolean[] hasHit = new boolean[3];

    private final boolean[] hasDoubled = new boolean[3];

    private int fjoldiHanda;

    private boolean fraNyjuVedmali;


    // ------------------- Handlerar -------------------------------------


    /**
     * Upphafsstillir nýjan blackjack leik.
     * Dealerinn fær eitt spil sem notandi fær að sjá.
     * Notandinn hefur þrjár hendur og tvö spil á hverri hönd
     * sem notandi getur veðjað á.
     */
    @FXML
    public void nyrLeikurHandler() {
        fxWinText.setVisible(false);
        fxArrowImage.setX(0);
        fxDoubleButton.setDisable(false);
        fxStandButton.setDisable(false);
        fxHitButton.setDisable(false);
        fxNyrLeikurButton.setDisable(true);
        fxNyttVedmalButton.setDisable(true);
        numerLeikmanns = fjoldiHanda-1;
        fxArrowImage.setX(-(2-(numerLeikmanns))*288);

        int totalBet = 0;

        for (int i = 0; i < 3; i++) {
            leikmennHbox[i].getChildren().clear();
            isBusted[i] = false;
            hasHit[i] = false;
            fxHendur[i].setText("");
        }


        for (int i = 0; i < 3; i++) {
            if (hasDoubled[i] && !fraNyjuVedmali) {
                fxBets[i].setText((Integer.parseInt(fxBets[i].getText())/2)+ "");
            }
            hasDoubled[i] = false;


            try {
                totalBet += Integer.parseInt(fxBets[i].getText());
            }
            catch (NumberFormatException ignored) {

            }
        }

        fraNyjuVedmali = false;

        Peningur.PENINGUR -= totalBet;
        fxCashText.setText("" + Peningur.PENINGUR);


        fxDealerHbox.getChildren().clear();

        stokkur = new Stokkur();
        dealer.nyrLeikur();


        for (Leikmadur l : leikmenn) {
            l.nyrLeikur();
        }

        for (int j = 0; j < fjoldiHanda; j++) {

            for (int i = 0; i < 2; i++) {
                SpilV leikMadurSpil = stokkur.dragaSpil();
                leikmenn[j].gefaSpil(leikMadurSpil);
                Spil vidmotsSpil = new Spil();
                vidmotsSpil.setSpil(leikMadurSpil);
                leikmennHbox[j].getChildren().add(vidmotsSpil);
            }
            fxHendur[j].setText("" + leikmenn[j].getSamtals());
        }

        SpilV dealerSpilSynilegt = stokkur.dragaSpil();
        dealer.gefaSpil(dealerSpilSynilegt);
        fxHondDealers.setText("" + dealer.getSamtals());

        dealerSpilFalid = stokkur.dragaSpil();
        dealer.gefaSpil(dealerSpilFalid);

        Spil dealerVidmotsSpil = new Spil();
        dealerVidmotsSpil.setSpil(dealerSpilSynilegt);
        fxDealerHbox.getChildren().add(dealerVidmotsSpil);

    }

    /**
     * Handler fyrir þegar leikmaður
     * vill ekki fá fleiri spil. Leikmaður spilar
     * þá næstu hönd ef hann á hana eftir.
     */
    @FXML
    public void komidNogHandler() {
        fxDoubleButton.setDisable(false);
        if (numerLeikmanns > 0) {
            numerLeikmanns--;
            fxArrowImage.setX(-(2-numerLeikmanns)*288); // Færir örina yfir á næstu hönd
        }
        else {

            Spil dealerVidmotsSpilFalid = new Spil();
            dealerVidmotsSpilFalid.setSpil(dealerSpilFalid);
            fxDealerHbox.getChildren().add(dealerVidmotsSpilFalid);

            while (dealer.getSamtals() < 17 && dealer.getFjoldiSpila() < 5) {
                SpilV s = stokkur.dragaSpil();
                dealer.gefaSpil(s);
                Spil synilegtSpil = new Spil();
                synilegtSpil.setSpil(s);
                fxDealerHbox.getChildren().add(synilegtSpil);

            }
            fxHondDealers.setText(dealer.getSamtals() + "");
            leikurBuinn();
        }
    }

    /**
     * Gefur leikmanni eitt nýtt spil.
     * Ef hann fer yfir 21 springur höndin og
     * hann þarf að spila næstu hönd ef hann á hana eftir.
     */
    @FXML
    public void nyttSpilHandler() {
        hasHit[numerLeikmanns] = true;
        fxDoubleButton.setDisable(true);

        SpilV s = stokkur.dragaSpil();
        leikmenn[numerLeikmanns].gefaSpil(s);


        Spil spil = new Spil();
        spil.setSpil(s);
        leikmennHbox[numerLeikmanns].getChildren().add(spil);
        fxHendur[numerLeikmanns].setText("" + leikmenn[numerLeikmanns].getSamtals());

        if (leikmenn[numerLeikmanns].getSamtals() > 21) {

            isBusted[numerLeikmanns] = true;

            fxWinText.setVisible(true);
            showText(fxWinText, "Hönd " + (numerLeikmanns+1) + " sprungin");

            if (numerLeikmanns > 0) {
                numerLeikmanns--;
                fxArrowImage.setX(-288 * (2 - numerLeikmanns));
                fxDoubleButton.setDisable(false);
            }
            else {
                komidNogHandler();
            }

        }
        if (leikmenn[numerLeikmanns].getFjoldiSpila() >= 5) {
            komidNogHandler();
        }

    }

    /**
     * Leyfir leikmanni að breyta veðmáli sínu
     * og byrjar nýjan leik.
     */
    public void nyttVedmalHandler() throws IOException {
        fraNyjuVedmali = true;

        for (int i = 0; i < 3; i++) {
            fxBets[i].setText("");
        }

        for (int i = 0; i < 3; i++) {

            ButtonType cancel = new ButtonType("Hætta við", ButtonBar.ButtonData.CANCEL_CLOSE);

            TextInputDialog textInputDialog = new TextInputDialog();

            textInputDialog.setTitle("Blackjack");

            textInputDialog.getDialogPane().setContentText("Veðmál");
            textInputDialog.setContentText("Settu inn upphæð veðmáls fyrir hönd " + (i+1));
            textInputDialog.setHeaderText("Veðmál");

            if (i > 0) {
                cancel = new ButtonType("Ekki fleiri hendur", ButtonBar.ButtonData.CANCEL_CLOSE);
            }
            textInputDialog.getDialogPane().getButtonTypes().set(1,cancel);

            Optional<String> result = textInputDialog.showAndWait();

            if (result.isPresent()) {
                if(!checkIfLegalBettingNumber(result.get())) {
                    illegalNumber();
                    goBackHandler(null);
                    return;
                }
                fxBets[i].setText(result.get());
                fjoldiHanda = i+1;
            } else {
                if (i == 0) {
                    goBackHandler(null);
                }
                else {
                    break;
                }
            }
        }

        nyrLeikurHandler();

    }

    /**
     * Leyfir leikmanni að tvöfalda veðmálið sitt.
     * Leikmaður fær eitt nýtt spil á höndina sína,
     * veðmálið fyrir þá hönd tvöfaldast en hann getur ekki
     * fengið nýtt spil fyrir þá hönd.
     * @param event
     */
    public void doubleHandler(ActionEvent event) {
        fxDoubleButton.setDisable(true);
        hasDoubled[numerLeikmanns] = true;
        int currentNumerLeikmanns = numerLeikmanns;
        fxBets[numerLeikmanns].setText("" + (2*Integer.parseInt(fxBets[numerLeikmanns].getText())));

        nyttSpilHandler();
        if (currentNumerLeikmanns == numerLeikmanns) // Ef leikmaður sprengdi ekki
            komidNogHandler();
    }

    /**
     * Handler sem skiptir yfir á
     * aðalvalmynd þegar ýtt er á til baka takkann.
     * @param event
     * @throws IOException
     */
    public void goBackHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("NewMainMenu-view.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    // ------------------------- Initializer --------------------------------


    /**
     * Initialize aðferð fyrir Blackjack.
     * Upphafsstillir allar tilviksbreytur,
     * tekur inn veðmál og byrjar nýjan leik.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fxCashText.setText("" + Peningur.PENINGUR);

        leikmennHbox[0] = fxLeikMadurHbox1;
        leikmennHbox[1] = fxLeikMadurHbox2;
        leikmennHbox[2] = fxLeikMadurHbox3;

        fxBets[0] = fxBet1;
        fxBets[1] = fxBet2;
        fxBets[2] = fxBet3;

        fxHendur[0] = fxHond1;
        fxHendur[1] = fxHond2;
        fxHendur[2] = fxHond3;

        for (int i = 0; i < 3; i++) {
            fxBets[i].setText("");
            fxBets[i].setDisable(true);
        }


        try {
            nyttVedmalHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // ------------------------ Vinnsla -------------------------

    // Eina vinnslufallið í þessum klasa. ,
    // Fallið notar margar global breytur og því óæskilegt
    // að búa til sér fall í vinnsluklasa með mörgum parametrum.


    /**
     * Fall sem keyrir í hvert skipti sem leikur klárast.
     * Fallið athugar hvaða hendur vinna og
     * sýnir skilaboð ef notandinn vinnur
     * einhver veðmál.
     */
    public void leikurBuinn()  {

        int totalWin = 0;

        int dealerSumma = dealer.getSamtals();

        String[] stringBets = {fxBet1.getText(), fxBet2.getText(), fxBet3.getText()};

        // Upphafsstilla bets fylki
        int[] bets = new int[3];
        for (int i = 0; i < 3; i++) {
            try {
                bets[i] = Integer.parseInt(stringBets[i]);
            }
            catch (NumberFormatException ignored) {}
        }


        if (dealerSumma > 21) {
            for (int i = 0; i < 3; i++) {
                if (!isBusted[i]) {
                    totalWin += 2 * bets[i];
                }
            }
        }
        // Ef dealer og leikmaður hafa sömu samtölu er jafntefli, þ.e. leikmaður fær endurgreitt
        else {
            for (int i = 0; i < 3; i++) {
                if (!isBusted[i]) {
                    if (dealerSumma < leikmenn[i].getSamtals()) {
                        totalWin += 2 * bets[i];
                    }
                    else if (dealerSumma == leikmenn[i].getSamtals())
                        totalWin += bets[i];
                }
            }
        }


        Peningur.PENINGUR += totalWin;
        fxCashText.setText("" + Peningur.PENINGUR);

        if (totalWin > 0) {
            fxWinText.setText("Þú vannst " + totalWin + "kr!");
            fxWinText.setVisible(true);
        }

        fxStandButton.setDisable(true);
        fxHitButton.setDisable(true);
        fxDoubleButton.setDisable(true);
        fxNyrLeikurButton.setDisable(false);
        fxNyttVedmalButton.setDisable(false);
    }

}
