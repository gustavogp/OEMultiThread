
import java.io.*;
import javax.swing.text.rtf.RTFEditorKit;


public class rtfExtract {
	
	public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) throws IOException {
		RTFEditorKit rtfEd = null;
		javax.swing.text.Document rtfDoc = null;
		FileInputStream readStr = null;
		String test = null;
		String path = "/Users/gustavopinheiro/Desktop/moinho/" + order;
		int dot = order.lastIndexOf(".");
		String extension = order.substring(dot + 1);
			
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
            xmlBuilder.qtyArrayBuilder(test, order, totalAmount, soldTo);
            xmlBuilder.elementBuilder(soldTo, shipTo, pO);
            if (isLast) {
            xmlBuilder.closeXML();
            }
            if ((extension.equalsIgnoreCase("doc")) || extension.equalsIgnoreCase("docx")) {
            	System.out.println(".doc or .docx processed as RTF successfully");
            }

            document.close();
		}catch (Exception e) {
			System.err.format("Exception in Main: %s%n", e);
        }finally {
        	if (readStr != null) {
        		readStr.close();
        	}
        }

	}
	
}




