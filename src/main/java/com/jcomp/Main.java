package com.jcomp;

import javafx.application.Application;
import javafx.stage.Stage;

public final class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("XYZ UML editor");
        primaryStage.setScene(new UMLEditor().init());
        primaryStage.show();
    }

    /**
     * Main body of App.
     * OOAD class term project V1.0
     * @param args The arguments of the program.
     */
    public final static void main(String[] args) {
        launch(args);
    }
}
