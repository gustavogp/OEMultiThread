import java.io.*;

import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import org.xml.sax.helpers.*;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Document;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.*;
import java.util.regex.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.swing.text.rtf.RTFEditorKit;

public class TextBag {
	static StreamResult streamResult;
    static TransformerHandler handler;
    static AttributesImpl atts;
    
    public static void main(String[] args) throws IOException {
		BufferedReader readStrBuff =null;
		StringBuilder strBuff = null;
		String test = null;
		char[] strArray = new char[100000];
		
		FileInputStream readStr = null; //POI does not support buffered stream
		HSSFWorkbook wb = null;
		ExcelExtractor extractor = null;
		
		PDFTextStripper reader = null;
		
		/** XLS extractor **/
/*		try {
			String path = "/Users/gustavopinheiro/Desktop/moinho/bag.xls";
			Document document = new Document();
            document.open();
            
            readStr = new FileInputStream(path);
            wb = new HSSFWorkbook (new POIFSFileSystem(readStr));
            extractor = new ExcelExtractor(wb);
            test = extractor.getText();
            streamResult = new StreamResult("/Users/gustavopinheiro/Desktop/testbag.xml");
            initXML();
            process(test);
            closeXML();

            document.close();
		}catch (Exception e) {
			System.err.format("IOException extracting xls: %s%n", e);
        }finally {
        	if (readStr != null) {
        		readStr.close();
        	}
        }  */
		
		/** DOC text extractor**/
/*		try {
			Document document = new Document();
            document.open();
            
            readStrBuff = new BufferedReader(new FileReader("/Users/gustavopinheiro/Desktop/moinho/bag.Doc"));
            readStrBuff.read(strArray, 0, 99999);
            strBuff = new StringBuilder();
            strBuff.append(strArray);
            test = strBuff.toString();
            streamResult = new StreamResult("/Users/gustavopinheiro/Desktop/testbag.xml");
            initXML();
            process(test);
            closeXML();
            document.add(new Paragraph(".."));
            document.close();
		}catch (Exception e) {
			System.err.format("IOException in Main: %s%n", e);
        }finally {
        	if (readStrBuff != null) {
        		readStrBuff.close();
        	}
        }	*/
		
			/** html extractor **/
		try {
			Document document = new Document();
            document.open();
            
            readStrBuff = new BufferedReader(new FileReader("/Users/gustavopinheiro/Desktop/moinho/bag.html"));
            readStrBuff.read(strArray, 0, 99999);
            strBuff = new StringBuilder();
            strBuff.append(strArray);
            test = strBuff.toString();
            streamResult = new StreamResult("/Users/gustavopinheiro/Desktop/testbag.xml");
            initXML();
            process(test);
            closeXML();
            document.close();
		}catch (Exception e) {
			System.err.format("IOException in Main: %s%n", e);
        }finally {
        	if (readStrBuff != null) {
        		readStrBuff.close();
        	}
        }

		/**pdf extractor**/
/*		try {          
            String path = "/Users/gustavopinheiro/Desktop/moinho/bag.pdf";
            
        	reader = new PDFTextStripper("ISO-8859-1");
            PDDocument document = PDDocument.load(path);
            test = reader.getText(document);
            streamResult = new StreamResult("/Users/gustavopinheiro/Desktop/testbag.xml");
            initXML();
            process(test);
            closeXML();
            document.close();
        } catch (Exception e) {
        	System.err.format("IOException in Main: %s%n", e);
        }  
	*/	
		/**rtf extractor **/
/*		try {
			String path = "/Users/gustavopinheiro/Desktop/moinho/bag.rtf";
			com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            document.open();
            
            RTFEditorKit rtfEd = new RTFEditorKit();
            javax.swing.text.Document rtfDoc = rtfEd.createDefaultDocument();
            readStr = new FileInputStream(path);
            
            rtfEd.read( readStr, rtfDoc, 0);
            
            test = rtfDoc.getText( 0, rtfDoc.getLength());
            streamResult = new StreamResult("/Users/gustavopinheiro/Desktop/testbag.xml");
            initXML();
            process(test);
            closeXML();
            document.close();
		}catch (Exception e) {
			System.err.format("Exception in Main: %s%n", e);
        	}	*/
    }
	
	public static void initXML() throws ParserConfigurationException,
		TransformerConfigurationException, SAXException {
			SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory
            .newInstance();

			handler = tf.newTransformerHandler();
			Transformer serializer = handler.getTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			serializer.setOutputProperty(
            "{http://xml.apache.org/xslt}indent-amount", "4");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			handler.setResult(streamResult);
			handler.startDocument();
			atts = new AttributesImpl();
			handler.startElement("", "", "Order", atts);
	}

	public static void process(String s) throws SAXException {
		String[] elements = s.split("\\s+");
		int i =0;
		while (i<elements.length) {
			atts.clear();
			handler.startElement("", "", "Field", atts);
			handler.characters(elements[i].toCharArray(), 0, elements[i].length());
			handler.endElement("", "", "Field");
			i++;
		}
	}

	public static void process_two(String s) throws SAXException {
		String searchMe = s;
		String matc = null;
		int j=0;
		String[] partNumbers = new String[100];
		String[] qty = new String[100];
		Pattern findMe = Pattern.compile("[mM]{1}"+"\\w{6}"+"/"+"[a-zA-Z]{1}");
		Pattern findMeEAN = Pattern.compile("[8]{2}"+"[0-9]{7}");
		
		for (int i = 0; i< (searchMe.length()-9); i++) {
			matc = searchMe.substring(i, i+9);
			if (findMe.matcher(matc).matches() || findMeEAN.matcher(matc).matches()) {
				partNumbers[j] = searchMe.substring(i, i+9);
				qty[j] = "QTY("+j+")";
				j++;
			}
			if (partNumbers.length==0) {
				partNumbers[0]="No Match Found";
			}
		}
		
		for (int k=0; k<j; k++) {
			atts.clear();
			handler.startElement("", "", "PartNumber", atts);
			handler.characters(partNumbers[k].toCharArray(), 0, partNumbers[k].length());
			handler.endElement("", "", "PartNumber");
			handler.startElement("", "", "Qty", atts);
  			handler.characters(qty[k].toCharArray(), 0, qty[k].length());
  			handler.endElement("", "", "Qty");
		}
	}

	public static void closeXML() throws SAXException {
		handler.endElement("", "", "Order");
		handler.endDocument();
	}
}
