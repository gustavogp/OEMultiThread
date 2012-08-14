import java.io.*;
import java.util.List;
import java.util.Set;

import com.itextpdf.text.Document;




public class htmlExtract {
	
	public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) throws IOException {
		BufferedReader readStrBuff =null;
		StringBuilder strBuff = null;
		String test = null;
		char[] strArray = new char[100000];
		String path = "/Users/gustavopinheiro/Desktop/moinho/" + order;
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
		}catch (Exception e) {
			System.err.format("IOException in Main: %s%n", e);
        }finally {
        	if (readStrBuff != null) {
        		readStrBuff.close();
        	}
        }

	}
	
}
