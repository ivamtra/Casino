package hi.verkefni.vinnnsla;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.HashMap;

/******************************************************************************
 *  Nafn    : Ívan Már Þrastarson
 *
 *  T-póstur: imt1@hi.is
 *
 *
 *  Lýsing  : Vinnsluklasi fyrir Rúlettu. Inniheldur föll sem upphafsstilla
 *  gagnagrindur, fall sem keyrist þegar leikmaður vinnur og fall
 *  sem finnur hvaða tölu rúllettan lendir á.
 *
 *
 *****************************************************************************/

public class RouletteVinnsla {

    /**
     * Skilar boolean fylki sem segja hvaða
     * tölur eru rauðar. Indexin í þessu fylki
     * samsvara indexunum í numbers fylkinu
     * í RouletteController.
     * @return isRed fylkinu
     */
    public static boolean[] setIsRed() {
        boolean[] isRed = new boolean[38];
        for (int i = 0; i < 38; i++) {
            isRed[i] = i % 2 != 0;
        }
        // Grænn
        isRed[19] = false;
        return isRed;
    }

    /**
     * Upphafsstillir hakkatöflu sem samsvarar
     * numbers fylkinu í RouletteController.
     * @param numbers fylki af rúlettutölum
     * @return numberMap hakkataflan.
     */
    public static HashMap<Integer, Integer> setHashMap(int[] numbers) {
        HashMap<Integer, Integer> numberMap = new HashMap<>();
        for (int i = 0; i < 38; i++) {
            numberMap.put(numbers[i], i);
        }
        return numberMap;
    }

    /**
     * Fall sem keyrir þegar notandi vinnur veðmál.
     * Global breytan PENINGUR hækkar eftir veðmáli
     * leikmanns og það birtast skilaboð sem tilkynna
     * notanda að hann hafi unnið.
     * @param fxMoneyText textinn sem sýnir upphæð penings.
     * @param fxWinText textinn sem birtist þegar maður vinnur.
     * @param vedmal upphæð veðmáls
     * @param vedmalScaler hversu mikið veðmálið margfaldast með þegar maður vinnur.
     */
    public static void winner(Text fxMoneyText, Text fxWinText, int vedmal, int vedmalScaler)  {
        // arguments, fxMoneyText, fxWinText, vedmal, vedmalScaler
        Peningur.PENINGUR += vedmal*vedmalScaler;
        fxMoneyText.setText("" + Peningur.PENINGUR);

        fxWinText.setText("Þú vannst " + vedmal*vedmalScaler + "kr!");
        fxWinText.setVisible(true);

        KeyFrame winnerKeyframe = new KeyFrame(Duration.millis(2000),
                e -> {
                    fxWinText.setVisible(true);
                });

        Timeline showTextTimeline = new Timeline(winnerKeyframe);

        showTextTimeline.setCycleCount(1);
        showTextTimeline.play();
        showTextTimeline.setOnFinished(e -> {
            fxWinText.setVisible(false);
        });
    }

    /**
     * Skilar tölunni sem bendirinn
     * í rúlettunni bendir á.
     * @param fxRouletteImage myndin af rúlettuhjólinu
     * @param numbers fylki af rúlettutölum
     * @return talan sem rúlettan lendir á.
     */
    public static int getnumber(ImageView fxRouletteImage, int[] numbers) {
        double angle = fxRouletteImage.getRotate() % 360;

        double angleOfNumber = 360 - angle;

        double offSet = -2.6; // Hliðrun í myndinni
        double step = 360/38.0; // Gráður sem hver tala tekur

        int iterator = 0;

        // Ítrar í gegnum rúlettuhjólið sjálft þangað til
        // að talan finnst.
        while(iterator*step - offSet < angleOfNumber) {
            iterator++;
        }
        try {
            return numbers[iterator];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return  -1;
        }
    }

    /**
     * Athugar hvort að talan sem fengin
     * er úr textfieldinu er á rúlettuhjólinu.
     * @param fxTextField TextField sem á að athuga
     * @return true ef hún er á hjólinu, annars false.
     */
    public static boolean checkIfLegalRouletteNumber(TextField fxTextField) {
        int i;
        try {
            i = Integer.parseInt(fxTextField.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        return i <= 38 && i >= 0;
    }


}
