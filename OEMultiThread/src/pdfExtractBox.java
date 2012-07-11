import java.io.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class pdfExtractBox {
    
    public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) throws IOException {
		String test = null;
		PDFTextStripper reader = null;
		String path = "/Users/gustavopinheiro/Desktop/moinho/" + order;
		
            try {
            	reader = new PDFTextStripper("ISO-8859-1");
                PDDocument document = PDDocument.load(path);
                    test = reader.getText(document);
                    if (isFirst) {
                    xmlBuilder.initXML();
                    }
                    xmlBuilder.pnArrayBuilder(test, soldTo);
                    xmlBuilder.qtyArrayBuilder(test, order, totalAmount, soldTo);
                    xmlBuilder.elementBuilder(soldTo, shipTo, pO);
                    if (isLast) {
                    xmlBuilder.closeXML();
                    }
                    document.close();
            } catch (Exception e) {
            	System.err.format("IOException in Main: %s%n", e);
            }
    }
}
