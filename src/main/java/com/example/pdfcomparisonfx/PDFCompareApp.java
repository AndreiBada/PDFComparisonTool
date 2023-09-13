
package com.example.pdfcomparisonfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PDFCompareApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PDFCompareApp.class.getResource("PDFComparator.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        stage.setTitle("PDFComparisonTool");
        stage.setScene(scene);

//        stage.setWidth(600);
//        stage.setHeight(420);
//        stage.setResizable(false);

        stage.show();

    }


    public static void main(String[] args) {
        PDFCompareController controller = new PDFCompareController();
        launch();
    }
}