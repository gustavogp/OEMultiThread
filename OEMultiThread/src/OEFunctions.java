import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class OEFunctions {
	static Map<String,Integer> tableNumberByShipTo;
	
	
	public static void tableNumberByShipToBuilder() {
		tableNumberByShipTo = new LinkedHashMap<String, Integer>();
		String soldPath = OEMultiT.inputPath.getParent() + "/soldToMap.xlsx";
		FileInputStream readStr = null; //POI does not support buffered stream
		XSSFWorkbook wb = null;
		XSSFSheet tableNumberSheet = null;
		String shipTo = null;
		int tableNumber = 0;
		
		try {
			readStr = new FileInputStream (soldPath);
			wb = new XSSFWorkbook(readStr);
			
			tableNumberSheet = wb.getSheetAt(4);
			for (Row row : tableNumberSheet) {
				if (row.getRowNum() > 3) {
					shipTo = (new Double(row.getCell(0).getNumericCellValue())).toString().substring(0, 6);
					tableNumber = (new Double(row.getCell(1).getNumericCellValue())).intValue();
					tableNumberByShipTo.put(shipTo, tableNumber);
				}
				
			}
			System.out.print(tableNumberByShipTo);//so para testar, deletar em seguida

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void priceTablesTableBuilder() {
		
	}
	public static void tableBuilder() {
		
	}
}
