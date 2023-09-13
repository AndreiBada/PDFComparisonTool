package com.example.pdfcomparisonfx;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import de.redsix.pdfcompare.PdfComparator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.pdfcomparisonfx.PDFtakeFirstPage.getFirstPage;

public class PDFCompareController implements Initializable {

    public List<File> files;
    public File file;
    public Label dropFileConf;
    public List<String> tipuriReperHL = new ArrayList<> ();
    public List<String> tipuriReperDC = new ArrayList<> ();
    public TextField InputTipEmboss;

    @FXML
    private ListView<String> ListMasterTypeHL;
    @FXML
    private ListView<String> ListMasterTypeDC;



    //Define file location for Master files
    String folderPathHL = "C:\\PDFComparisonFX blank\\src\\main\\MasterDocumentsHL";
    String folderPathDC = "C:\\PDFComparisonFX blank\\src\\main\\MasterDocumentsDC";

    File folderHL = new File ((folderPathHL));
    File folderDC = new File ((folderPathDC));


    // Constructor:
    public PDFCompareController() {
        //Add master document to target folder MasterDocuments
        File[] filesHL = folderHL.listFiles ();
        //iterate the files array
        assert filesHL != null;
        for (File file : filesHL) {
            //check if the file exists
            if (file.isFile ()) {
                //Add new file to the list
                tipuriReperHL.add (file.getAbsolutePath ());
            }

        }

        File[] filesDC = folderDC.listFiles ();
        //iterate the files array
        assert filesDC != null;
        for (File fileDC : filesDC) {
            //check if the file exists
            if (fileDC.isFile ()) {
//                System.out.println ("File ->" + file.getName ());
                //Add new file to the list
                tipuriReperDC.add (fileDC.getAbsolutePath ());

            }
        }
    }



    //Show added documents as list view
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        String folderPath = "C:\\PDFComparisonFX blank\\src\\main\\MasterDocumentsHL";
//        String folderPath = folderPathHL;
//        String folderPathDC = "C:\\PDFComparisonFX blank\\src\\main\\MasterDocumentsDC";
//        String folderPathDC = folderPathDC;

        File folderHL = new File (folderPathHL);
        File folderDC = new File (folderPathDC);

        File[] files = folderHL.listFiles ();
        File[] filesDC = folderDC.listFiles ();
        //iterate the files array
        assert files != null;
        assert filesDC != null;

        for (File file : files) {
            //check if the file exists
            if (file.isFile ()) {
                ListMasterTypeHL.getItems ().add (file.getName ());
            }
        }
        for (File file : filesDC) {
            //check if the file exists
            if (file.isFile ()) {
                ListMasterTypeDC.getItems ().add (file.getName ());
            }
        }
    }

    @FXML
    protected void onCompareHLButtonClick() throws Exception {
        //Compare and write files
        for (File f : files) {
            List<InputStream> inputPdfList;
            OutputStream outputStream;
            inputPdfList = new ArrayList<> ();
            int n = 1;

            for (String p : tipuriReperHL) {
                String result = "C:\\PDFComparisonFX blank\\src\\main\\Results\\ResultCompare" + n;
                try {
                    new PdfComparator<> (p, f.getAbsolutePath ()).compare ().writeTo (result);
                    String toConcatenate1 = "C:\\PDFComparisonFX blank\\src\\main\\Results\\ResultCompare" + n + ".pdf";

                    //WRITE custom text inside each document
                    String DEST = "C:\\PDFComparisonFX blank\\src\\main\\WriteResults\\WtritedPDF" + n + ".pdf";
                    PdfStamper pdfStamper = null;
                    try {
                        //Create PdfReader instance.
                        PdfReader pdfReader = new PdfReader (toConcatenate1);

                        //Create PdfStamper instance.
                        pdfStamper = new PdfStamper (pdfReader, new FileOutputStream (DEST));

                        //Create BaseFont instance.
                        BaseFont baseFont = BaseFont.createFont (
                                BaseFont.TIMES_ROMAN,
                                BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

                        //Get the number of pages in pdf.
//                        int pages = pdfReader.getNumberOfPages ();
//                        System.out.println (pdfStamper.getOverContent (1));

                        //Contain the pdf data.
                        PdfContentByte pageContentByte = pdfStamper.getOverContent (1);
                        pageContentByte.setFlatness (89);
                        pageContentByte.beginText ();

                        //Set text font and size. Set position in the page
                        pageContentByte.setFontAndSize (baseFont, 40);
                        pageContentByte.setColorFill (BaseColor.GREEN);
//                    pageContentByte.setTextMatrix (300, 720);
                        pageContentByte.setTextMatrix (400, 200);

                        //Write text
                        pageContentByte.setWordSpacing (12);

                        //Regex
                        String regex = p.substring (0, p.length ());
                        Pattern pattern = Pattern.compile ("(\\w\\d+)");
                        Matcher matcher = pattern.matcher (regex);
                        if (matcher.find ()) {
                            pageContentByte.showText (matcher.group ());
                        }

                        // Add new text line
                        pageContentByte.setFontAndSize (baseFont, 40);
                        pageContentByte.setColorFill (BaseColor.RED);
                        pageContentByte.setTextMatrix (400, 150);

                        //Write text
                        pageContentByte.setWordSpacing (12);
                        String text = f.getName ().substring (0, 7);
                        pageContentByte.showText (text);

                        pageContentByte.endText ();
                        pdfStamper.close ();

                        System.out.println ("PDF written successfully.");

                    } catch (Exception e) {
                        e.printStackTrace ();
                    }

                    inputPdfList.add (new FileInputStream (DEST));

                } catch (Exception e) {
                    e.printStackTrace ();
                }
                n++;
            }

            // Open file target
            String toOpen = "C:\\PDFComparisonFX blank\\src\\main\\Results\\ResultConcatenate.pdf";

            //Prepare output stream for merged pdf file.
            outputStream = new FileOutputStream (toOpen);

            //call method to merge pdf files.
            Concatenate.mergePdfFiles (inputPdfList, outputStream);

            System.out.println ("Concatenation completed!");

            //open new PDF (toOpen) file
            try {
                Runtime.getRuntime ().exec ("rundll32 url.dll, FileProtocolHandler " + toOpen);
            } catch (Exception e) {
                JOptionPane.showMessageDialog (null, "Check your file details");
            }
            System.out.println ("File opened!");
        }
    }

    public void onCompareDCButtonClick(ActionEvent actionEvent) throws Exception {

        //Compare and write files
        for (File f : files) {
            String toOpen = "C:\\PDFComparisonFX blank\\src\\main\\ResultsDC\\ResultConcatenateDC.pdf";
            List<InputStream> inputPdfList;
            OutputStream outputStream;
            inputPdfList = new ArrayList<> ();
            int n = 1;
            for (String p : tipuriReperDC) {
                String result = "C:\\PDFComparisonFX blank\\src\\main\\ResultsDC\\ResultCompare" + n;
                try {
                    new PdfComparator<> (p, f.getAbsolutePath ()).compare ().writeTo (result);
                    String toConcatenate1 = "C:\\PDFComparisonFX blank\\src\\main\\ResultsDC\\ResultCompare" + n + ".pdf";

                    //WRITE custom text inside each document
                    String DEST = "C:\\PDFComparisonFX blank\\src\\main\\WrotePDF" + n + ".pdf";
                    PdfStamper pdfStamper = null;
                    try {
                        //Create PdfReader instance.
                        PdfReader pdfReader = new PdfReader (toConcatenate1);

                        //Create PdfStamper instance.
                        pdfStamper = new PdfStamper (pdfReader, new FileOutputStream (DEST));

                        //Create BaseFont instance.
                        BaseFont baseFont = BaseFont.createFont (
                                BaseFont.TIMES_ROMAN,
                                BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

                        //Get the number of pages in pdf.
//                        int pages = pdfReader.getNumberOfPages ();
//                        System.out.println (pdfStamper.getOverContent (1));

                        //Contain the pdf data.
                        PdfContentByte pageContentByte = pdfStamper.getOverContent (1);
                        pageContentByte.setFlatness (89);
                        pageContentByte.beginText ();

                        //Set text font and size. Set position in the page
                        pageContentByte.setFontAndSize (baseFont, 40);
//                    pageContentByte.setTextMatrix (300, 720);
                        pageContentByte.setColorFill (BaseColor.GREEN);
                        pageContentByte.setTextMatrix (500, 800);

//                    pageContentByte.setFontAndSize (baseFont, 60);
//                    pageContentByte.setTextMatrix (700 , 1000);

                        //Write text
                        pageContentByte.setWordSpacing (12);
//                    pageContentByte.showText ("Embossing Type DCT" + n);

                        //Regex
                        String regex = p.substring (0, p.length ());
                        Pattern pattern = Pattern.compile ("(\\w\\d+)");
                        Matcher matcher = pattern.matcher (regex);
                        if (matcher.find ()) {
                            pageContentByte.showText (matcher.group ());
                        }

                        //Write New Line of Text
                        //Set text font and size. Set position in the page
                        pageContentByte.setFontAndSize (baseFont, 40);
                        pageContentByte.setColorFill (BaseColor.RED);
                        pageContentByte.setTextMatrix (500, 700);

                        //Write text
                        pageContentByte.setWordSpacing (12);
                        String text = f.getName ().substring (0, 7);
                        pageContentByte.showText (text);


                        pageContentByte.endText ();
                        pdfStamper.close ();

                        System.out.println ("PDF written successfully.");
                    } catch (Exception e) {
                        e.printStackTrace ();
                    }

                    inputPdfList.add (new FileInputStream (DEST));

                } catch (Exception e) {
                    e.printStackTrace ();
                }
                n++;
            }

            //Prepare output stream for merged pdf file.
            outputStream = new FileOutputStream (toOpen);

            //call method to merge pdf files.
            Concatenate.mergePdfFiles (inputPdfList, outputStream);

            System.out.println ("Concatenation completed!");

            //open new PDF (toOpen) file
            try {
                Runtime.getRuntime ().exec ("rundll32 url.dll, FileProtocolHandler " + toOpen);
            } catch (Exception e) {
                JOptionPane.showMessageDialog (null, "Check your file details");
            }

            System.out.println ("File opened!");
        }
    }


    public void onDragOver(DragEvent dragEvent) {
        if (dragEvent.getDragboard ().hasFiles ()) {
            dragEvent.acceptTransferModes (TransferMode.ANY);
        }
    }

    public void onDragDropped(DragEvent dragEvent) {
        if (dragEvent.getDragboard ().hasFiles ()) {
            files = (List<File>) dragEvent.getDragboard ().getFiles ();

//            file = dragEvent.getDragboard ().getFiles ();
            getFirstPage (file = dragEvent.getDragboard ().getFiles ().get (0));
            dropFileConf.setText ("File(s) added for comparison!");

//            dropFileConf.setText(file.getName() + " added for comparison!");
        }
    }

    public void onDisplayButton(ActionEvent actionEvent) {
// Open a Windows app from button (e.g: notepad)
        try {
            Runtime r = Runtime.getRuntime ();
            r.exec ("notepad");
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public void onAddToMasterDoc(ActionEvent actionEvent) {
        String numeFisier = InputTipEmboss.getText ();

        for (File f : files) {
//            File originalFile = new File(file.getAbsolutePath());m
            File originalFile = new File (f.getAbsolutePath ());

            File newFile = new File ("C:\\PDFComparisonFX blank\\src\\main\\MasterDocumentsHL\\" + numeFisier + ".pdf");

            try {
                Files.copy (originalFile.toPath (), newFile.toPath ());
                tipuriReperHL.add (newFile.getAbsolutePath ());
                ListMasterTypeHL.getItems ().add (newFile.getName ());
                System.out.println ("New File added to MasterDocuments!");
            } catch (Exception e) {
                System.out.println ("Error");
                e.printStackTrace ();
            }
        }
    }

    public void onAddToMasterDCDoc(ActionEvent actionEvent) {
        String numeFisier = InputTipEmboss.getText ();

        for (File f : files) {
            File originalFile = new File (f.getAbsolutePath ());
            File newFile = new File ("C:\\PDFComparisonFX blank\\src\\main\\MasterDocumentsDC\\" + numeFisier + ".pdf");

            try {
                Files.copy (originalFile.toPath (), newFile.toPath ());
                tipuriReperDC.add (newFile.getAbsolutePath ());
                ListMasterTypeDC.getItems ().add (newFile.getName ());
                System.out.println ("New File added to MasterDocuments!");
            } catch (Exception e) {
                System.out.println ("Error");
                e.printStackTrace ();
            }
        }
    }


    public void onDeleteHLbutton(ActionEvent actionEvent) {
        int selectedID = ListMasterTypeHL.getSelectionModel ().getSelectedIndex ();
        String fileName = ListMasterTypeHL.getItems ().get (selectedID);

        for (File f : Objects.requireNonNull (folderHL.listFiles ())) {
            if (f.getName ().equals (fileName)) {
                ListMasterTypeHL.getItems ().remove (selectedID);
                f.delete ();
                break;
            }
        }
    }


    public void onDeleteDCbutton(ActionEvent actionEvent) {
        int selectedID = ListMasterTypeDC.getSelectionModel ().getSelectedIndex ();
        String fileName = ListMasterTypeDC.getItems ().get (selectedID);

        for (File f : Objects.requireNonNull (folderDC.listFiles ())) {
            if (f.getName ().equals (fileName)) {
                ListMasterTypeDC.getItems ().remove (selectedID);
                f.delete ();
                break;
            }
        }
    }


}
