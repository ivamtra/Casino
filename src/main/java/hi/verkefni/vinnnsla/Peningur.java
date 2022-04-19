package hi.verkefni.vinnnsla;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/******************************************************************************
 *  Nafn    : Ívan Már Þrastarson
 *
 *  T-póstur: imt1@hi.is
 *
 *
 *  Lýsing  : Vinnsluklasi fyrir pening. Inniheldur eina global breytu
 *  sem samsvarar pening leikmanns og föll sem athuga hvort
 *  að löglegar aðgerðir eru framkvæmdar með peningana.
 *
 *
 *****************************************************************************/

public class Peningur {
    public static int PENINGUR;

    /**
     * Athugar hvort að veðmál
     * sé lögleg upphæð
     * @param s strengur
     * @return true ef s er löglegt veðmál, annars false
     */
    public static boolean checkIfLegalBettingNumber(String s) {
        int number;
        try {
            number = Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return number >= 0;
    }

    /**
     * Birtir Alert-Dialog sem
     * segir notanda að hann hafi
     * sett inn ólöglegt veðmál.
     */
    public static void illegalNumber() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Casino");
        alert.setHeaderText("Ólöglegt veðmál");
        alert.setContentText("Vinsamlegast settu inn löglegt veðmál");
        alert.show();
    }
}
