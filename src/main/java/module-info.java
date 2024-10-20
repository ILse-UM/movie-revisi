module com.um.movie {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    requires jdk.compiler;

    opens com.um.movie to javafx.fxml;
    opens com.um.movie.controller to javafx.fxml;
    exports com.um.movie;
}