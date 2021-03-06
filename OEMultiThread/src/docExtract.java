
import java.io.*;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.apache.poi.hwpf.extractor.*;
import org.xml.sax.SAXException;

import com.itextpdf.text.Document;


public class docExtract {

	public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) {
		FileInputStream readStr = null; //POI does not support buffered stream
		WordExtractor extractor = null;
		String test = null;
		String path = OEMultiT.inputPath + "/" + order;
		Set<String> pnSet;
		List<Double> prices;
		
		int tableNumber = OEFunctions.tableNumberByShipTo.get(shipTo);
			
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
            pnSet = xmlBuilder.hSet;
            
            prices = OEFunctions.priceArrayBuilder(tableNumber, pnSet);
            xmlBuilder.qtyArrayBuilder(test, order, totalAmount, soldTo, prices);
            if (!(xmlBuilder.qty[0] == null)) {
            	xmlBuilder.elementBuilder(soldTo, shipTo, pO);
            }
            if (isLast) {
            xmlBuilder.closeXML();
            }

            document.close();
		} catch(IllegalArgumentException e) {
			System.err.format("IllegalArgumentException in docExtractor", e);
			e.printStackTrace();
			System.out.println("Sending to rtfExtract.java");
			rtfExtract.main( order, soldTo, shipTo, pO, totalAmount, isFirst, isLast);
		} catch (IOException e) {
			System.err.format("IOException in docExtractor: %s%n", e);
			if ((e.toString().contains("rtf")) || e.toString().contains("RTF")) {
				System.out.println("Sending to rtfExtract.java");
				rtfExtract.main( order, soldTo, shipTo, pO, totalAmount, isFirst, isLast);
			}
        } catch (TransformerConfigurationException e) {
        	System.err.format("TransformerConfigurationException in docExtractor: %s%n", e);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.err.format("ParserConfigurationException in docExtractor: %s%n", e);
			e.printStackTrace();
		} catch (SAXException e) {
			System.err.format("SAXException in docExtractor: %s%n", e);
			e.printStackTrace();
		}finally {
        	if (readStr != null) {
        		try {
					readStr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }

	}
	
}



