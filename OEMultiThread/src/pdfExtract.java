import java.io.*;
import java.util.List;
import java.util.Set;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class pdfExtract {
    static TransformerHandler handler;
    static AttributesImpl atts;
    

    public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) {
    	PdfReader reader = null;
		try {
			reader = new PdfReader("/Users/gustavopinheiro/Desktop/Pedido Apple - ES 45_38311_02.09.11.pdf");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	Set<String> pnSet;
		List<Double> prices;
		
		int tableNumber = OEFunctions.tableNumberByShipTo.get(shipTo);
		
            try {
                    Document document = new Document();
                    document.open();
                    PdfDictionary page = reader.getPageN(1);
                    PRIndirectReference objectReference = (PRIndirectReference) page
                                    .get(PdfName.CONTENTS);
                    PRStream stream = (PRStream) PdfReader
                                    .getPdfObject(objectReference);
                    byte[] streamBytes = PdfReader.getStreamBytes(stream);
                    PRTokeniser tokenizer = new PRTokeniser(streamBytes);

                    StringBuffer strbufe = new StringBuffer();
                    while (tokenizer.nextToken()) {
                            if (tokenizer.getTokenType() == PRTokeniser.TokenType.STRING) {
                                    strbufe.append(tokenizer.getStringValue());
                            }
                    }
                    String test = strbufe.toString();
                    xmlBuilder.initXML();
                    xmlBuilder.pnArrayBuilder(test, soldTo);
                    pnSet = xmlBuilder.hSet;
                    
                    prices = OEFunctions.priceArrayBuilder(tableNumber, pnSet);
                    xmlBuilder.qtyArrayBuilder(test, order, totalAmount, soldTo, prices);
                    xmlBuilder.elementBuilder(soldTo, shipTo, pO);
                    xmlBuilder.closeXML();
                    document.add(new Paragraph(".."));
                    document.close();
            } catch (IOException e) {
            	System.err.format("IOException in pdfExtract: %s%n", e);
            	e.printStackTrace();
            } catch (TransformerConfigurationException e) {
            	System.err.format("TransformerConfigurationException in pdfExtract: %s%n", e);
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				System.err.format("ParserConfigurationException in pdfExtract: %s%n", e);
				e.printStackTrace();
			} catch (SAXException e) {
				System.err.format("SAXException in pdfExtract: %s%n", e);
				e.printStackTrace();
			} catch (DocumentException e) {
				System.err.format("DocumentException in pdfExtract: %s%n", e);
				e.printStackTrace();
			}finally {
            	if (reader != null) {
            		reader.close();
            	}
            }
    }
}
