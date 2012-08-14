
import java.io.*;
import java.util.List;
import java.util.Set;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import com.itextpdf.text.Document;



public class docxExtract {
	
	public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) throws IOException {
		FileInputStream readStr = null; //POI does not support buffered stream
		XWPFDocument wDoc = null;
		XWPFWordExtractor extractor = null;
		String test = null;
		String path = "/Users/gustavopinheiro/Desktop/moinho/" + order;
		Set<String> pnSet;
		List<Double> prices;
		
		int tableNumber = OEFunctions.tableNumberByShipTo.get(shipTo);
			
		try {
			Document document = new Document();
            document.open();
            
            readStr = new FileInputStream(path);
            wDoc = new XWPFDocument (readStr);
            extractor = new XWPFWordExtractor(wDoc);
            
            test = extractor.getText();
            if (isFirst) {
            xmlBuilder.initXML();
            }
            xmlBuilder.pnArrayBuilder(test, soldTo);
            pnSet = xmlBuilder.hSet;
            
            prices = OEFunctions.priceArrayBuilder(tableNumber, pnSet);
            xmlBuilder.qtyArrayBuilder(test, order, totalAmount, soldTo, prices);
            xmlBuilder.elementBuilder(soldTo, shipTo, pO);
            if (isLast) {
            xmlBuilder.closeXML();
            }

            document.close();
		}catch (Exception e) {
			System.err.format("Exception in Main: %s%n", e);
			if ((e.toString().contains("rtf")) || e.toString().contains("RTF")) {
				System.out.println("Sending to rtfExtract.java");
				rtfExtract.main( order, soldTo, shipTo, pO, totalAmount, isFirst, isLast);
			}
        }finally {
        	if (readStr != null) {
        		readStr.close();
        	}
        }

	}
	
}




