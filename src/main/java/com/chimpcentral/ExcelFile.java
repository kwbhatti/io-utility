package com.chimpcentral;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFile extends NinjaFile {

    private XSSFWorkbook workbook;
    FileOutputStream fileOutputStream;
    FileInputStream fileInputStream;
    
	public ExcelFile(String filepath, FileStatus fileStatus) throws IOException, InvalidFormatException {
		super(filepath, fileStatus);
		System.out.println("input stream set");
		setWorkbook();
	}
	
	@Override
	public ExcelFile getCopy(String destFilepath) throws IOException, InvalidFormatException {
		createCopy(destFilepath);
		return new ExcelFile(destFilepath, FileStatus.EXISTING);
	}
	
	private void setWorkbook() throws InvalidFormatException, IOException {
		if (fileCreatedCheck instanceof New) {
			workbook = new XSSFWorkbook();
		} else if (fileCreatedCheck instanceof Existing) {
			FileInputStream fileInputStream = new FileInputStream(file);
			workbook = new XSSFWorkbook(fileInputStream);
		}
	}
	
	public XSSFWorkbook getWorkbook() {
		return workbook;
	}
	
	public void createSheet(String sheetName) {
		workbook.createSheet(sheetName);
	}
	
	public XSSFSheet getSheet(String sheetName) {
		return workbook.getSheet(sheetName);
	}

	public int getSearchRowIndex(String sheetName, String searchRow) {
		Integer searchRowIndex = null;
		XSSFSheet sheet = getSheet(sheetName);
		Iterator<Row> iterator = sheet.rowIterator();
		while(iterator.hasNext()) {
			Row row = iterator.next();
			Cell cell = row.getCell(0);
			String cellValue = cell.getStringCellValue();
			if (cellValue.equals(searchRow)) {
				searchRowIndex = cell.getRowIndex();
			}
		}
		return searchRowIndex;
	}
	
	public int getSearchColumnIndex(String sheetName, String searchCol) {
		Integer searchColIndex = null;
		Row firstRow = getSheet(sheetName).getRow(0);
		Iterator<Cell> iterator = firstRow.cellIterator();
		while (iterator.hasNext()) {
			Cell cell = iterator.next();
			String cellValue = cell.getStringCellValue();
			if (cellValue.equals(searchCol)) {
				searchColIndex = cell.getColumnIndex();
			}
		}
		return searchColIndex.intValue();
	}
	
	public String getCellValueAsString(Cell cell) {
		DataFormatter formatter = new DataFormatter();
		return formatter.formatCellValue(cell);
	}

	public String getCellValueAsString(String sheetName, int rowIndex, int colIndex) {
		return getCellValueAsString(getSheet(sheetName).getRow(rowIndex).getCell(colIndex));
	}
	
	public String getCellValueAsString(String sheetName, int rowIndex, String seachCol) {
		return getCellValueAsString(getSheet(sheetName).getRow(rowIndex).getCell(getSearchColumnIndex(sheetName, seachCol)));
	}
	
	public String getCellValueAsString(String sheetName, String searchRow, int colIndex) {
		return getCellValueAsString(getSheet(sheetName).getRow(getSearchRowIndex(sheetName, searchRow)).getCell(colIndex));
	}
	
	public String getCellValueAsString(String sheetName, String searchRow, String seachCol) {
		return getCellValueAsString(getSheet(sheetName).getRow(getSearchRowIndex(sheetName, searchRow)).getCell(getSearchColumnIndex(sheetName, seachCol)));
	}
	
	public void setCellValue(String sheetName, int rowIndex, int colIndex, String value) {
		Row row;
		try {
			row = getSheet(sheetName).getRow(rowIndex);
			rowIndex = row.getRowNum();
		} catch (NullPointerException e) {
			row = getSheet(sheetName).createRow(rowIndex);
		}
		Cell cell;
		try {
			cell = row.getCell(colIndex);
			colIndex = cell.getColumnIndex();
		} catch (NullPointerException e) {
			cell = row.createCell(colIndex);
		}
		cell.setCellValue(value);
	}
	
	public void setCellValue(String sheetName, int rowIndex, String searchCol, String value) {
		setCellValue(sheetName, rowIndex, getSearchColumnIndex(sheetName, searchCol), value);
	}
	
	public void setCellValue(String sheetName, String searchRow, int colIndex, String value) {
		setCellValue(sheetName, getSearchRowIndex(sheetName, searchRow), colIndex, value);
	}
	
	public void setCellValue(String sheetName, String searchRow, String searchCol, String value) {
		setCellValue(sheetName, getSearchRowIndex(sheetName, searchRow), getSearchColumnIndex(sheetName, searchCol), value);
	}
	
	public void flush() throws IOException {
		fileOutputStream = new FileOutputStream(file);
		workbook.write(fileOutputStream);
		fileOutputStream.close();
		workbook.close();
	}
}
