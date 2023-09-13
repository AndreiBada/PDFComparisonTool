module com.example.pdfcomparisonfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires itextpdf;
    requires pdfcompare;
    requires java.desktop;
    requires pdf.util;


    opens com.example.pdfcomparisonfx to javafx.fxml;
    exports com.example.pdfcomparisonfx;
}