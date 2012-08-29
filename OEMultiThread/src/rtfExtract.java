
import java.io.*;
import java.util.List;
import java.util.Set;

import javax.swing.text.BadLocationException;
import javax.swing.text.rtf.RTFEditorKit;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.xml.sax.SAXException;


public class rtfExtract {
	
	public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) {
		RTFEditorKit rtfEd = null;
		javax.swing.text.Document rtfDoc = null;
		FileInputStream readStr = null;
		String test = null;
		String path = OEMultiT.inputPath + "/" + order;
		int dot = order.lastIndexOf(".");
		String extension = order.substring(dot + 1);
		Set<String> pnSet;
		List<Double> prices;
		
		int tableNumber = OEFunctions.tableNumberByShipTo.get(shipTo);
			
		try {
			com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            document.open();
            
            rtfEd = new RTFEditorKit();
            rtfDoc = rtfEd.createDefaultDocument();
            readStr = new FileInputStream(path);
            
            rtfEd.read( readStr, rtfDoc, 0);
            
            test = rtfDoc.getText( 0, rtfDoc.getLength());
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
            if ((extension.equalsIgnoreCase("doc")) || extension.equalsIgnoreCase("docx")) {
            	System.out.println(".doc or .docx processed as RTF successfully");
            }

            document.close();
		}catch (IOException e) {
			System.err.format("IOException in rtfExtract: %s%n", e);
			e.printStackTrace();
        } catch (BadLocationException e) {
        	System.err.format("BadLocationException in rtfExtract: %s%n", e);
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			System.err.format("TransformerConfigurationException in rtfExtract: %s%n", e);
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.err.format("ParserConfigurationException in rtfExtract: %s%n", e);
			e.printStackTrace();
		} catch (SAXException e) {
			System.err.format("SAXException in rtfExtract: %s%n", e);
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




