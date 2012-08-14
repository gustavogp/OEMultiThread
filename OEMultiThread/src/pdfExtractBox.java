import java.io.*;
import java.util.List;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class pdfExtractBox {
    
    public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) throws IOException {
		String test = null;
		PDFTextStripper reader = null;
		String path = "/Users/gustavopinheiro/Desktop/moinho/" + order;
		Set<String> pnSet;
		List<Double> prices;
		
		int tableNumber = OEFunctions.tableNumberByShipTo.get(shipTo);
		
            try {
            	reader = new PDFTextStripper("ISO-8859-1");
                PDDocument document = PDDocument.load(path);
                    test = reader.getText(document);
                    if (isFirst) {
                    xmlBuilder.initXML();
                    }
                    xmlBuilder.pnArrayBuilder(test, soldTo);
                    pnSet = xmlBuilder.hSet;
                    
                    prices = OEFunctions.priceArrayBuilder(tableNumber, pnSet);
                    System.out.println("prices in pdfExtractor: " + prices); //testing only, delete afterwards
                    
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
