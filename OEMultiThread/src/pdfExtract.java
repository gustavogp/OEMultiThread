import java.io.*;
import org.xml.sax.helpers.*;
import javax.xml.transform.sax.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class pdfExtract {
    static TransformerHandler handler;
    static AttributesImpl atts;

    public static void main(String order, String soldTo, String shipTo, String pO, String totalAmount, boolean isFirst, boolean isLast) throws IOException {
    	PdfReader reader = new PdfReader("/Users/gustavopinheiro/Desktop/Pedido Apple - ES 45_38311_02.09.11.pdf");
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
                    xmlBuilder.qtyArrayBuilder(test, order, totalAmount, soldTo);
                    xmlBuilder.elementBuilder(soldTo, shipTo, pO);
                    xmlBuilder.closeXML();
                    document.add(new Paragraph(".."));
                    document.close();
            } catch (Exception e) {
            	System.err.format("IOException in Main: %s%n", e);
            }finally {
            	if (reader != null) {
            		reader.close();
            	}
            }
    }
}
