package com.example.pdfcomparisonfx;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFtakeFirstPage extends PDFCompareController {

    public static File getFirstPage(File file) {
        try (PDDocument document = PDDocument.load (file)) {

            Splitter splitter = new Splitter ();
            List<PDDocument> Pages = splitter.split (document);
            PDDocument pd = Pages.get (0);

            pd.save (file);
            document.close ();


//            Iterator<PDDocument> iterator = Pages.listIterator();
//            while (iterator.hasNext()) {
//                pd = iterator.next();
//                pd.save(path);
//            }
//        }

        } catch (IOException e) {
            throw new RuntimeException (e);
        }
        return file;
    }
}
