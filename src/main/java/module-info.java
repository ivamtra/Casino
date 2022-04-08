module hi.verkefni.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires Spil21Library;


    exports hi.verkefni.vinnnsla;
    opens hi.verkefni.vinnnsla to javafx.fxml;

    exports hi.verkefni.vidmot;
    opens hi.verkefni.vidmot to javafx.fxml;
}