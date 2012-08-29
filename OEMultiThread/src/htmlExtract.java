import java.io.*;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.xml.sax.SAXException;

import com.itextpdf.text.Document;




public class htmlExtract {
	
	public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) {
		BufferedReader readStrBuff =null;
		StringBuilder strBuff = null;
		String test = null;
		char[] strArray = new char[100000];
		String path = OEMultiT.inputPath + "/" + order;
		Set<String> pnSet;
		List<Double> prices;
		
		int tableNumber = OEFunctions.tableNumberByShipTo.get(shipTo);
			
		try {
			Document document = new Document();
            document.open();
            
            readStrBuff = new BufferedReader(new FileReader(path));
            readStrBuff.read(strArray, 0, 99999);
            strBuff = new StringBuilder();
            strBuff.append(strArray);
            test = strBuff.toString();
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
			System.err.format("IOException in htmlExtractor: %s%n", e);
			e.printStackTrace();
        } catch (TransformerConfigurationException e) {
        	System.err.format("TransformerConfigurationException in htmlExtractor: %s%n", e);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.err.format("ParserConfigurationException in htmlExtractor: %s%n", e);
			e.printStackTrace();
		} catch (SAXException e) {
			System.err.format("SAXException in htmlExtractor: %s%n", e);
			e.printStackTrace();
		}finally {
        	if (readStrBuff != null) {
        		try {
					readStrBuff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }

	}
	
}
