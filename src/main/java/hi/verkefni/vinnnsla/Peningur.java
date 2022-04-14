package hi.verkefni.vinnnsla;

import javafx.scene.control.TextField;

/******************************************************************************
 *  Nafn    : Ívan Már Þrastarson
 *
 *  T-póstur: imt1@hi.is
 *
 *
 *  Lýsing  : Vinnsluklasi fyrir pening. Inniheldur eina global breytu
 *  sem samsvarar pening leikmanns.
 *
 *
 *****************************************************************************/

public class Peningur {
    public static int PENINGUR;

    public static boolean checkIfLegalBettingNumber(TextField fxTextField) {
        boolean isLegal = true;
        int number;
        try {
            number = Integer.parseInt(fxTextField.getText());
        }
        catch (NumberFormatException e) {
            return false;
        }
        return number >= 0;
    }
}
