import java.io.*;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.xml.sax.SAXException;

public class pdfExtractBox {
    
    public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) {
		String test = null;
		PDFTextStripper reader = null;
		String path = OEMultiT.inputPath + "/" + order;
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
                    
                    xmlBuilder.qtyArrayBuilder(test, order, totalAmount, soldTo, prices);
                    xmlBuilder.elementBuilder(soldTo, shipTo, pO);

                    if (isLast) {
                    xmlBuilder.closeXML();
                    }
                    document.close();
            } catch (IOException e) {
            	System.err.format("IOException in pdfExtractBox: %s%n", e);
            	e.printStackTrace();
            } catch (TransformerConfigurationException e) {
            	System.err.format("TransformerConfigurationException in pdfExtractBox: %s%n", e);
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				System.err.format("ParserConfigurationException in pdfExtractBox: %s%n", e);
				e.printStackTrace();
			} catch (SAXException e) {
				System.err.format("SAXException in pdfExtractBox: %s%n", e);
				e.printStackTrace();
			}
    }
}
