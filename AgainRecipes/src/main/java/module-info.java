module com.example.againrecipes {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.againrecipes to javafx.fxml;
    exports com.example.againrecipes;
}