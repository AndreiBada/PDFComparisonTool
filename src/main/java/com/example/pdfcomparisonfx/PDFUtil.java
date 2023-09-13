package com.example.pdfcomparisonfx;


import com.testautomationguru.utility.CompareMode;

import java.io.IOException;

public class PDFUtil {
    public static void main(String[] args) throws IOException {
        com.testautomationguru.utility.PDFUtil pdfUtil = new com.testautomationguru.utility.PDFUtil();
        String file1 = "C:\\Users\\rbadiric\\OneDrive - Philip Morris International\\Desktop\\T2\\40.9GFJ-NL04-DP-Ai_Layers-02.pdf";
        String file2 = "C:\\Users\\rbadiric\\OneDrive - Philip Morris International\\Desktop\\T3\\40.9GDZ-LT04-DP-Ai_Layers-01.pdf";
        String file3 = "C:\\Users\\rbadiric\\OneDrive - Philip Morris International\\Desktop\\T2\\40.9SKG-CZ04-DP-Ai_Layers-03.pdf";
        String result = "C:\\Users\\rbadiric\\Downloads";

        pdfUtil.setCompareMode(CompareMode.VISUAL_MODE);
        pdfUtil.highlightPdfDifference(true);
        pdfUtil.setImageDestinationPath(result);
        pdfUtil.compare(file1, file3);

    }
}
