package com.example.pdfcomparisonfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import org.w3c.dom.Text;


public class LoadingScreen implements Runnable {
    ProgressIndicator progressIndicator;
    Text loadingText;

    public LoadingScreen(ProgressIndicator progressIndicator, Text loadingText) {
        this.progressIndicator = progressIndicator;
        this.loadingText = loadingText;
    }

    @Override
    public void run() {
        while (progressIndicator.getProgress() <= 1) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    progressIndicator.setProgress(progressIndicator.getProgress() + 0.1);
                }
            });
            synchronized (this) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        loadingText.setTextContent("Done Loading");
    }
}



