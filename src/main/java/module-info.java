module hi.verkefni.main {
    requires javafx.controls;
    requires javafx.fxml;


    //opens hi.verkefni.vinnsla to javafx.fxml;
    //exports hi.verkefni.vinnsla;
    exports hi.verkefni.vidmot;
    opens hi.verkefni.vidmot to javafx.fxml;
}