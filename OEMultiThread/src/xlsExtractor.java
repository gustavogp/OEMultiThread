
import java.io.*;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.apache.poi.hssf.extractor.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.xml.sax.SAXException;

import com.itextpdf.text.Document;




public class xlsExtractor {
	
	public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) {
		FileInputStream readStr = null; //POI does not support buffered stream
		HSSFWorkbook wb = null;
		ExcelExtractor extractor = null;
		String test = null;
		String path = OEMultiT.inputPath + "/" + order;
		Set<String> pnSet;
		List<Double> prices;
		
		int tableNumber = OEFunctions.tableNumberByShipTo.get(shipTo);
			
		try {
			Document document = new Document();
            document.open();
            
            readStr = new FileInputStream(path);
            wb = new HSSFWorkbook (new POIFSFileSystem(readStr));
            extractor = new ExcelExtractor(wb);
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
			System.err.format("IOException in xlsExtract: %s%n", e);
			e.printStackTrace();
        } catch (TransformerConfigurationException e) {
        	System.err.format("TransformerConfigurationException in xlsExtract: %s%n", e);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.err.format("ParserConfigurationException in xlsExtract: %s%n", e);
			e.printStackTrace();
		} catch (SAXException e) {
			System.err.format("SAXException in xlsExtract: %s%n", e);
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

