
import java.io.*;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xssf.extractor.*;
import org.apache.xmlbeans.XmlException;
import org.xml.sax.SAXException;

import com.itextpdf.text.Document;


public class xlsxExtractor {

	
	public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) {
		FileInputStream readStr = null; //POI does not support buffered stream
		XSSFEventBasedExcelExtractor extractor = null;
		String test = null;
		String path = "/Users/gustavopinheiro/Desktop/moinho/" + order;
		Set<String> pnSet;
		List<Double> prices;
		
		int tableNumber = OEFunctions.tableNumberByShipTo.get(shipTo);
			
		try {
			Document document = new Document();
            document.open();
            
            
            extractor = new XSSFEventBasedExcelExtractor(path);
            
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
		}catch (IOException e) {
			System.err.format("IOException in xlsxExtract: %s%n", e);
			e.printStackTrace();
        } catch (TransformerConfigurationException e) {
        	System.err.format("TransformerConfigurationException in xlsxExtract: %s%n", e);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.err.format("ParserConfigurationException in xlsxExtract: %s%n", e);
			e.printStackTrace();
		} catch (SAXException e) {
			System.err.format("SAXException in xlsxExtract: %s%n", e);
			e.printStackTrace();
		} catch (XmlException e) {
			System.err.format("XmlException in xlsxExtract: %s%n", e);
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			System.err.format("OpenXML4JException in xlsxExtract: %s%n", e);
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


