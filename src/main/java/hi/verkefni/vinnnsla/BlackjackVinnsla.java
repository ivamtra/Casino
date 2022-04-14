package hi.verkefni.vinnnsla;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BlackjackVinnsla {

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
