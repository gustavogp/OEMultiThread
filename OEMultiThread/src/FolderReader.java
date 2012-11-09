import java.io.*;


public class FolderReader {
/**
 * counts how many files are in folder /Users/username/Desktop//moinho
 * determines type of each file
 * calls designated extractor for each file
 */
	public static String[] orderFiles = new String[100];
	public static boolean isFirstOrder;
	public static boolean isLastOrder;
	
	public static void main(File args) {
		try {
		orderFiles = args.list();
		isFirstOrder = true;
		isLastOrder = false;
		for (int i = 1; i<=(orderFiles.length-1); i++) {
			System.out.println(orderFiles[i]);
		}
		//set the maxvalue of the progressbar
		OEMultiT.progressBar.setValue(0);
		OEMultiT.progressBar.setMaximum((orderFiles.length - 1));
		System.out.println("Total Orders: " + (orderFiles.length - 1));
		selectExtractor();
		} catch (IOException e) {
			System.err.format("IOException in Main: %s%n", e);
			OEMultiT.invalidFineNameMessage();
		}
	}
	public static void selectExtractor () throws IOException {
		String[] newSoldShipArray = new String[2];
		boolean soldShipOk = true;
		
		OEFunctions.tableNumberByShipToBuilder(); //build the table only 1x
		OEFunctions.priceTablesTableBuilder(); //build the table only 1x
		
		for (int i = 1; i<= (orderFiles.length-1); i++) {
			if (i == (orderFiles.length-1)) {
				isLastOrder = true;
			}
			int dot = 0;
			String soldTo = null;
			String remaining1 = null;
			String shipTo = null;
			String remaining2 = null;
			String pO = null;
			String remaining3 = null;
			String totalAmount = null;
			String extension = null;
			
			try {
				dot = orderFiles[i].indexOf(".");
				soldTo = orderFiles[i].substring(0, dot);
				remaining1 = orderFiles[i].substring(dot + 1);
				dot = remaining1.indexOf(".");
				shipTo = remaining1.substring(0, dot);
				remaining2 = remaining1.substring(dot + 1);
				dot = remaining2.indexOf(".");
				pO = remaining2.substring(0, dot);
				remaining3 = remaining2.substring(dot +1);
				dot = remaining3.indexOf('.');
				totalAmount = remaining3.substring(0, dot);
				dot = orderFiles[i].lastIndexOf(".");
				extension = orderFiles[i].substring(dot + 1);
			} catch (StringIndexOutOfBoundsException e) {
				System.err.format("IOException in Main: %s%n", e);
				OEMultiT.invalidFineNameMessage();
			}
			
			
			//change file name
			newSoldShipArray[0] = null;
			newSoldShipArray[1] = null;
			
			ChangeFileName.buildSoldAndShipMaps();
			
			try {
				newSoldShipArray = ChangeFileName.changeSoldTo(soldTo, shipTo);
				@SuppressWarnings("unused")
				int lenghtTest = newSoldShipArray[0].length(); //using this just to trigger the NullPointer Exception
				@SuppressWarnings("unused")
				int lenghtTest2 = newSoldShipArray[1].length(); //using this just to trigger the NullPointer Exception
			} catch (NullPointerException e) {
				System.out.println("Invalid Sold-To or Ship-To: " + soldTo + ", " + shipTo);
				OEMultiT.invalidSoldShipMessage(soldTo, shipTo);
				soldShipOk = false; //this will interrupt this iteration of the loop, could have used resume
				break;//this will stop the loop for the remaining orders
			}
			
			//only go ahead if Sold-To and Ship-To are ok
			if (soldShipOk) {
				int tableNumber;
				
				soldTo = newSoldShipArray[0];
				shipTo = newSoldShipArray[1];

				tableNumber = OEFunctions.tableNumberByShipTo.get(shipTo); // testing only, delete afterwards

				System.out.println(extension);
				if (extension.equalsIgnoreCase("pdf")) {
					pdfExtractBox.main(orderFiles[i], soldTo, shipTo, pO, totalAmount, isFirstOrder, isLastOrder);
					System.out.println("\n"+i + ", price table # :" + tableNumber);
					OEMultiT.archivedMessage(i + "/" + (orderFiles.length - 1));
					OEMultiT.progressBar.setValue(i);
				} else if (extension.equalsIgnoreCase("html"))  {
					htmlExtract.main(orderFiles[i], soldTo, shipTo, pO, totalAmount, isFirstOrder, isLastOrder);
					System.out.println("\n"+i + ", price table # :" + tableNumber);
					OEMultiT.archivedMessage(i + "/" + (orderFiles.length - 1));
					OEMultiT.progressBar.setValue(i);
				} else if (extension.equalsIgnoreCase("htm")) {
					htmlExtract.main(orderFiles[i], soldTo, shipTo, pO, totalAmount, isFirstOrder, isLastOrder);
					System.out.println("\n"+i + ", price table # :" + tableNumber);
					OEMultiT.archivedMessage(i + "/" + (orderFiles.length - 1));
					OEMultiT.progressBar.setValue(i);
				} else if (extension.equalsIgnoreCase("txt")) {
					htmlExtract.main(orderFiles[i], soldTo, shipTo, pO, totalAmount, isFirstOrder, isLastOrder);
					System.out.println("\n"+i + ", price table # :" + tableNumber);
					OEMultiT.archivedMessage(i + "/" + (orderFiles.length - 1));
					OEMultiT.progressBar.setValue(i);
				} else if (extension.equalsIgnoreCase("xls")) {
					xlsExtractor.main(orderFiles[i], soldTo, shipTo, pO, totalAmount, isFirstOrder, isLastOrder);
					System.out.println("\n"+i + ", price table # :" + tableNumber);
					OEMultiT.archivedMessage(i + "/" + (orderFiles.length - 1));
					OEMultiT.progressBar.setValue(i);
				} else if (extension.equalsIgnoreCase("xlsx")) {
					xlsxExtractor.main(orderFiles[i], soldTo, shipTo, pO, totalAmount, isFirstOrder, isLastOrder);
					System.out.println("\n"+i + ", price table # :" + tableNumber);
					OEMultiT.archivedMessage(i + "/" + (orderFiles.length - 1));
					OEMultiT.progressBar.setValue(i);
				} else if (extension.equalsIgnoreCase("doc")) {
					docExtract.main(orderFiles[i], soldTo, shipTo, pO, totalAmount, isFirstOrder, isLastOrder);
					System.out.println("\n"+i + ", price table # :" + tableNumber);
					OEMultiT.archivedMessage(i + "/" + (orderFiles.length - 1));
					OEMultiT.progressBar.setValue(i);
				} else if (extension.equalsIgnoreCase("docx")) {
					docxExtract.main(orderFiles[i], soldTo, shipTo, pO, totalAmount, isFirstOrder, isLastOrder);
					System.out.println("\n"+i + ", price table # :" + tableNumber);
					OEMultiT.archivedMessage(i + "/" + (orderFiles.length - 1));
					OEMultiT.progressBar.setValue(i);
				} else if (extension.equalsIgnoreCase("rtf")) {
					rtfExtract.main(orderFiles[i], soldTo, shipTo, pO, totalAmount, isFirstOrder, isLastOrder);
					System.out.println("\n"+i + ", price table # :" + tableNumber);
					OEMultiT.archivedMessage(i + "/" + (orderFiles.length - 1));
					OEMultiT.progressBar.setValue(i);
				}
				isFirstOrder = false;
			}
		}
	}
}
