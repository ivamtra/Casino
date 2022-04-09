package hi.verkefni.vidmot;

import hi.verkefni.vinnnsla.Leikmadur;
import hi.verkefni.vinnnsla.Peningur;
import hi.verkefni.vinnsla.SpilV;
import hi.verkefni.vinnsla.Stokkur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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

    private Leikmadur[] leikmenn = new Leikmadur[]{leikmadur1, leikmadur2, leikmadur3};

    // Dealerinn hefur alltaf eitt falið spil í venjulegum blackjack.
    // Mig langaði að hafa það í þessu forriti
    // þannig ég nota þessa tilviksbreytu til að útfæra það.
    private SpilV dealerSpilFalid;



    @FXML
    private HBox fxLeikMadurHbox1, fxLeikMadurHbox2, fxLeikMadurHbox3, fxDealerHbox;

    @FXML
    private HBox[] leikmennHbox = new HBox[3];

    @FXML
    private Button fxHitButton, fxStandButton, fxDoubleButton;

    @FXML
    private ImageView fxArrowImage;

    private int numerLeikmanns = 2;

    @FXML
    private Text fxCashText, fxWinText;

    @FXML
    private TextField fxBet1, fxBet2, fxBet3;

    @FXML
    private TextField[] fxBets = new TextField[3];

    private boolean[] isBusted = new boolean[3];

    private boolean[] hasHit = new boolean[3];


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
        numerLeikmanns = 2;

        for (int i = 0; i < 3; i++) {
            leikmennHbox[i].getChildren().clear();
            isBusted[i] = false;
            hasHit[i] = false;
        }


        fxDealerHbox.getChildren().clear();

        stokkur = new Stokkur();
        dealer.nyrLeikur();


        for (int i = 0; i < 3; i++) {
            leikmenn[i].nyrLeikur();
        }

        for (int j = 0; j < 3; j++) {

            for (int i = 0; i < 2; i++) {
                SpilV leikMadurSpil = stokkur.dragaSpil();
                leikmenn[j].gefaSpil(leikMadurSpil);
                Spil vidmotsSpil = new Spil();
                vidmotsSpil.setSpil(leikMadurSpil);
                leikmennHbox[j].getChildren().add(vidmotsSpil);
            }
        }

        dealerSpilFalid = stokkur.dragaSpil();
        dealer.gefaSpil(dealerSpilFalid);

        SpilV dealerSpilSynilegt = stokkur.dragaSpil();
        dealer.gefaSpil(dealerSpilSynilegt);

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
            fxArrowImage.setX(-(2-numerLeikmanns)*288);
            System.out.println(numerLeikmanns);
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

        if (leikmenn[numerLeikmanns].getSamtals() > 21) {

            isBusted[numerLeikmanns] = true;

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
            System.out.println("Of mörg spil");
            komidNogHandler();
            return;
        }

    }


    /**
     * Leyfir leikmanni að breyta veðmáli sínu
     * og byrjar nýjan leik.
     */
    public void nyttVedmalHandler() {
        for (int i = 0; i < 3; i++) {
            TextInputDialog textInputDialog = new TextInputDialog();

            textInputDialog.setTitle("Blackjack");

            textInputDialog.getDialogPane().setContentText("Veðmál");
            textInputDialog.setContentText("Settu inn upphæð veðmáls fyrir hönd " + (i+1));
            textInputDialog.setHeaderText("Veðmál");

            Optional<String> result = textInputDialog.showAndWait();

            if (result.isPresent()) {
                System.out.println("nafnið er " + result.get());
                fxBets[i].setText(result.get());
            } else {
                fxBets[i].setText(0 + "");
                System.out.println("ekkert svar");
            }
            fxBets[i].setDisable(true);
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
        int currentNumerLeikmanns = numerLeikmanns;
        fxBets[numerLeikmanns].setText("" + (2*Integer.parseInt(fxBets[numerLeikmanns].getText())));

        nyttSpilHandler();
        if (currentNumerLeikmanns == numerLeikmanns)
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

        nyttVedmalHandler();

        nyrLeikurHandler();
    }


    // ------------------------ Vinnsla -------------------------

    // Eina vinnslufallið fyrir utan þau sem eru byggð inn í Leikmaður klasanum.
    // Fallið notar margar global breytur þannig það tekur því ekki
    // Að búa til sér klasa einungis fyrir þetta eina fall.


    /**
     * Fall sem keyrir í hvert skipti sem leikur klárast.
     * Fallið athugar hvaða hendur vinna og
     * sýnir skilaboð ef notandinn endar í
     * hagnaði.
     */
    public void leikurBuinn()  {

        int profitLoss = 0;

        int dealerSumma = dealer.getSamtals();

        System.out.println("dealerSumma " + dealerSumma);

        String[] stringBets = {fxBet1.getText(), fxBet2.getText(), fxBet3.getText()};

        // Upphafsstilla bets fylki
        int[] bets = new int[3];
        for (int i = 0; i < 3; i++) {
            try {
                bets[i] = Integer.parseInt(stringBets[i]);
            }
            catch (NumberFormatException ignored) {
            }
            System.out.println("hönd "+ i + " " + leikmenn[i].getSamtals());
        }

        // Athuga hverjir sprungu

        for (int i = 0; i < 3; i++) {
            if(isBusted[i]) {
                profitLoss -= bets[i];
            }
        }

        if (dealerSumma > 21) {
            for (int i = 0; i < 3; i++) {
                if (!isBusted[i])
                  profitLoss += bets[i];
            }
        }
        // Ef dealer og leikmaður hafa sömu samtölu er jafntefli, þ.e. leikmaður fær endurgreitt
        else {
            for (int i = 0; i < 3; i++) {
                if (!isBusted[i]) {
                    if (dealerSumma > leikmenn[i].getSamtals()) {
                        profitLoss -= bets[i];
                    }
                    else if (dealerSumma < leikmenn[i].getSamtals())
                        profitLoss += bets[i];
                }
            }
        }
        System.out.println(profitLoss);
        Peningur.PENINGUR += profitLoss;
        fxCashText.setText("" + Peningur.PENINGUR);

        if (profitLoss > 0) {
            fxWinText.setText("Þú vannst " + profitLoss + "kr!");
            fxWinText.setVisible(true);
        }

        fxStandButton.setDisable(true);
        fxHitButton.setDisable(true);
        fxDoubleButton.setDisable(true);
    }

}
