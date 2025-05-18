module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    
    opens app to javafx.graphics;
    opens com.example.demo to javafx.graphics;
    opens controller to javafx.fxml;
    opens dto to javafx.base;
    opens model to javafx.base;


    exports app;
    exports controller;
    exports dto;
    exports model;
}
