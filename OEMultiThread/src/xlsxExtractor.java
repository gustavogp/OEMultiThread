
import java.io.*;
import org.apache.poi.xssf.extractor.*;
import com.itextpdf.text.Document;


public class xlsxExtractor {

	
	public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) throws IOException {
		FileInputStream readStr = null; //POI does not support buffered stream
		XSSFEventBasedExcelExtractor extractor = null;
		String test = null;
		String path = "/Users/gustavopinheiro/Desktop/moinho/" + order;
			
		try {
			Document document = new Document();
            document.open();
            
            
            extractor = new XSSFEventBasedExcelExtractor(path);
            
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
			System.err.format("IOException in Main: %s%n", e);
        }finally {
        	if (readStr != null) {
        		readStr.close();
        	}
        }

	}
	
}


