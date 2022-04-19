package hi.verkefni.vinnnsla;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;

/******************************************************************************
 *  Nafn    : Ívan Már Þrastarson
 *
 *  T-póstur: imt1@hi.is
 *
 *
 *  Lýsing  : Vinnsluklasi fyrir Blackjack. Inniheldur aðferð sem
 *  birtir texta á skjáinn. Klasinn er mjög lítill núna en hann er gerður
 *  með það í huga á framtíðarviðbætum.
 *
 *
 *****************************************************************************/

public class BlackjackVinnsla {

    /**
     * Fallið tekur inn JavaFX
     * texta og streng og birtir
     * textann með innihaldi strengsins
     * í 1000ms.
     * @param fxText JavaFX textinn
     * @param text strengur sem maður vill að birtist
     */
    public static void showText(Text fxText, String text) {
        fxText.setText(text);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000),
                e -> {
                    fxText.setVisible(true);
                });

        Timeline timeline = new Timeline(keyFrame);

        timeline.setCycleCount(1);
        timeline.play();
        timeline.setOnFinished(
                e -> {
                    fxText.setVisible(false);
                }
        );

    }
}
