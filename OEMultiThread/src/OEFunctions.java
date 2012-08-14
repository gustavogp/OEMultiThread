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
	static Map<Integer,Map<String,Double>> priceTables;
	
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
		//	System.out.print(tableNumberByShipTo);//so para testar, deletar em seguida

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void priceTablesTableBuilder() {
		priceTables = new LinkedHashMap<Integer, Map<String,Double>>();
		String soldPath = OEMultiT.inputPath.getParent() + "/ListaPrecoMaster2012_06_22.xlsx";
		FileInputStream readStr = null; //POI does not support buffered stream
		XSSFWorkbook wb = null;
		XSSFSheet priceListSheet = null;
		String pn = null;
		double price = 0;
		
		try {
			readStr = new FileInputStream (soldPath);
			wb = new XSSFWorkbook(readStr);
			priceListSheet = wb.getSheetAt(0);
			
			for (int i = 0; i < 22; i++) {
				Map<String, Double> priceTableI = new LinkedHashMap<String, Double>();
				for (Row row : priceListSheet) {
					if (row.getRowNum() > 3) {
						pn = row.getCell(3).getStringCellValue();
						price = row.getCell(i+12).getNumericCellValue();
						priceTableI.put(pn, price);
					}
				}
			//	System.out.println(priceTableI); //only for testing, delete afterwards
				priceTables.put(i, priceTableI);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	//	System.out.println(priceTables.get(12));  //only for testing, delete afterwards
	}
	
	public static double[] priceArrayBuilder() {
		double[] priceArray;
		
		priceArray = null;
		
		return priceArray;
	}
}