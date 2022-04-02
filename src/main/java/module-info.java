module hi.verkefni.main {
    requires javafx.controls;
    requires javafx.fxml;


    opens hi.verkefni.main to javafx.fxml;
    exports hi.verkefni.main;
}