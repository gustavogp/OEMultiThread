import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class ChangeFileName {
	static Map<String, String> soldToMap;
	static Map<String, String> shipToMap;
	static String newSoldTo;
	static String newShipTo;
	
	
	public static void buildSoldAndShipMaps () {
		soldToMap = new LinkedHashMap<String, String>();
		shipToMap = new LinkedHashMap<String, String>();
		String soldPath = OEMultiT.inputPath.getParent() + "/soldToMap.xlsx";
		FileInputStream readStr = null; //POI does not support buffered stream
		XSSFWorkbook wb = null;
		XSSFSheet soldSheet = null;
		String soldTo = null;
		String soldToNumber = null;
		XSSFSheet shipSheet = null;
		String shipTo = null;
		String shipToNumber = null;

		if (true) {
			try {
				readStr = new FileInputStream (soldPath);
				wb = new XSSFWorkbook(readStr);
				
				//work on sold to
				soldSheet = wb.getSheetAt(0);
				for (Row row : soldSheet) {
					for (Cell cell : row) {
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							soldTo = cell.getRichStringCellValue().getString();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							soldToNumber = String.valueOf(cell.getNumericCellValue()).substring(0, 6);
							break;
						}
						soldToMap.put(soldTo, soldToNumber);
					}

				}
				//work on ship to
				shipSheet = wb.getSheetAt(1);
				for (Row row : shipSheet) {
					for (Cell cell : row) {
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							shipTo = cell.getRichStringCellValue().getString();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							shipToNumber = String.valueOf(cell.getNumericCellValue()).substring(0, 6);
							break;
						}
						shipToMap.put(shipTo, shipToNumber);
					}

				}
			//	System.out.println( soldToMap);
			//	System.out.println( shipToMap);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	public static void changeFileNames() {
		String[] orderFiles = new String[100];
		File file;
		
		orderFiles = OEMultiT.inputPath.list();
		for (int i = 1; i<= (orderFiles.length-1); i++) {
			int dot = orderFiles[i].indexOf(".");
			String soldTo = orderFiles[i].substring(0, dot);
			String remaining1 = orderFiles[i].substring(dot + 1);
			dot = remaining1.indexOf(".");
			String shipTo = remaining1.substring(0, dot);
			String remaining2 = remaining1.substring(dot + 1);
			dot = remaining2.indexOf(".");
			String pO = remaining2.substring(0, dot);
			String remaining3 = remaining2.substring(dot +1);
			dot = remaining3.indexOf('.');
			String totalAmount = remaining3.substring(0, dot);
			dot = orderFiles[i].lastIndexOf(".");
			String extension = orderFiles[i].substring(dot + 1);
			//retrieve data from maps
			newSoldTo = soldToMap.get(soldTo);
			newShipTo = shipToMap.get(shipTo);
			//rename
			System.out.println(newSoldTo + ", " + newShipTo);
		/*	file = new File(orderFiles[i]);
			if (file.canExecute() && file.canRead() && file.canWrite()) {
				if (file.renameTo(new File(OEMultiT.inputPath + "/" + newSoldTo + "." + newShipTo + "." + pO + "." + totalAmount + "." + extension))) {
					System.out.println("changing file name to " + file.getName());	
				}
			} else System.out.println("sem acesso");
			
			file.setWritable(true, true);
			if (file.canExecute() && file.canRead() && file.canWrite()) {
				if (file.renameTo(new File(OEMultiT.inputPath + "/" + newSoldTo + "." + newShipTo + "." + pO + "." + totalAmount + "." + extension))) {
					System.out.println("changing file name to " + file.getName());	
				}
			} else System.out.println("ainda sem acesso");
			System.out.println("trying to change file name to " + file.getName()); */
		} 
	}
	public static String[] changeSoldTo(String st, String shpt) {
		String[] newSoldShip = new String[2];
		newSoldShip[0] = soldToMap.get(st);
		newSoldShip[1] = shipToMap.get(shpt);
		return newSoldShip;
	}
}
