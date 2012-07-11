
import java.io.*;
import org.apache.poi.hwpf.extractor.*;
import com.itextpdf.text.Document;


public class docExtract {

	public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) throws IOException {
		FileInputStream readStr = null; //POI does not support buffered stream
		WordExtractor extractor = null;
		String test = null;
		String path = "/Users/gustavopinheiro/Desktop/moinho/" + order;
			
		try {
			Document document = new Document();
            document.open();
            
            readStr = new FileInputStream(path);         
            extractor = new WordExtractor(readStr);
            
            test = extractor.getText();
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



