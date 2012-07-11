import org.apache.poi.xssf.usermodel.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import java.util.*;


public class xlsxBuilder extends DefaultHandler {
	static XMLReader xr;
	static FileReader dataReader;
	public XSSFWorkbook wb;
	public XSSFSheet sheet1;
	public FileOutputStream fileOut;
	public Calendar todayNow = Calendar.getInstance();
	public int rowNumber = 0;
	public int colNumber = 0;
	public XSSFRow row;
	public XSSFCell cell;
	public String temp;
	public String soldTo;
	public String shipTo;
	public String pO;
	public int lineItem;
	public String concat;
	
	public static void main() {
		
		try {
				
			xr = XMLReaderFactory.createXMLReader();
			xr.setContentHandler(new xlsxBuilder());
						
			dataReader = new FileReader("/Users/gustavopinheiro/Desktop/data.xml");
			xr.parse(new InputSource(dataReader));
			     
			
		} catch (IOException ioe) {
			System.out.println("IOException in xlsxBuilder.main " + ioe);
		} catch (SAXException saxe) {
			System.out.println("SAXException in xlsxBuilder.main " + saxe.getMessage());
		}
	}

	/**Event Handlers
	 */
	public void startDocument() throws SAXException {
		wb = new XSSFWorkbook();
		sheet1 = wb.createSheet("CSAC BRZ");
		row = sheet1.createRow(rowNumber);
		row.createCell(0).setCellValue("SoldTo");
		row.createCell(1).setCellValue("ShipTo");
		row.createCell(2).setCellValue("PONum");
		row.createCell(3).setCellValue("Line Item");
		row.createCell(4).setCellValue("Part Number");
		row.createCell(5).setCellValue("Qty");
    	rowNumber++;
    	row = sheet1.createRow(rowNumber);
    }


    public void endDocument()  throws SAXException{
    	try {
    	fileOut = new FileOutputStream("/Users/gustavopinheiro/Desktop/output_excel/batch" + String.valueOf((todayNow.get(Calendar.MONTH)+1)) + String.valueOf(todayNow.get(Calendar.DAY_OF_MONTH)) + 
																	String.valueOf(todayNow.get(Calendar.HOUR_OF_DAY)) + String.valueOf(todayNow.get(Calendar.MINUTE)) + ".xlsx");
        wb.write(fileOut);
        fileOut.close();
    } catch (FileNotFoundException fnfe) {
    	System.out.println ("FileNotFoundException in xlsxBuilder.main()" + fnfe);
    }catch (IOException ioe) {
    	System.out.println ("IOException in xlsxBuilder.main()" + ioe);
    } 
    }

    public void startElement(String uri, String name,
			      String qName, Attributes atts) throws SAXException {
 
	if ("Batch".equals (qName)) {
	
    } else if ("Order".equals (qName)) {
    	lineItem = 1;
    } else if ("SoldTo".equals (qName)) {
    	colNumber = 0;
    	cell = row.createCell(colNumber);
    } else if ("ShipTo".equals (qName)) {
    	colNumber = 1;
    	cell = row.createCell(colNumber);
    } else if ("PO".equals (qName)) {
    	colNumber = 2;
    	cell = row.createCell(colNumber);
    }else if ("Item".equals (qName)) {
        colNumber = 3;
        cell = row.createCell(colNumber);
        cell.setCellValue(new Integer(lineItem).toString());
    } else if ("PartNumber".equals (qName)) {
    	colNumber = 4;
    	cell = row.createCell(colNumber);
    } else if ("Qty".equals (qName)) {
    	colNumber = 5;
    	cell = row.createCell(colNumber);
    }
    }
	    

    public void endElement(String uri, String name, String qName) throws SAXException {
    	
    	if ("Batch".equals (qName)) {
    		row.createCell(0).setCellValue("");
    		row.createCell(1).setCellValue("");
    		row.createCell(2).setCellValue("");
        } else if ("Order".equals (qName)) {
        	//code to include concatenate formula
        	concat = "CONCATENATE("+"A" + rowNumber + ",\", \", " + "C" + rowNumber + ",\", \", " + "G" + rowNumber + ")";
        	sheet1.getRow(rowNumber-1).createCell(7).setCellFormula(concat);
        } else if ("SoldTo".equals (qName)) {
            cell.setCellValue(temp);
            soldTo = temp;
        } else if ("ShipTo".equals (qName)) {
            cell.setCellValue(temp);
            shipTo = temp;
        } else if ("PO".equals (qName)) {
            cell.setCellValue(temp);
            pO = temp;
  //      	colNumber = 3;
  //      	cell = row.createCell(colNumber);
        } else if ("Item".equals (qName)) {
        	lineItem++;
    	    rowNumber++;
        	row = sheet1.createRow(rowNumber);
    	    row.createCell(0).setCellValue(soldTo);
    	    row.createCell(1).setCellValue(shipTo);
    	    row.createCell(2).setCellValue(pO);
 //   	    cell = row.createCell(3);
    	    
        } else if ("PartNumber".equals (qName)) {
        	cell.setCellValue(temp);
        } else if ("Qty".equals (qName)) {
        	cell.setCellValue(temp);
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException   {
		StringBuilder strBuff = new StringBuilder();
			strBuff.append(ch);
		temp = strBuff.toString().substring(start, start+length);

    }
    
}
