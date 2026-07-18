package com.mbm.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExcelUtils {

	private ExcelUtils() {

	}

	public static List<Map<String, String>> getDataFromExcel(String filePath,String sheetName) {
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> map;
		try(FileInputStream fis = new FileInputStream(filePath);
				XSSFWorkbook workbook = new XSSFWorkbook(fis);) {
			XSSFSheet sheet = workbook.getSheet(sheetName);
			int rows = sheet.getLastRowNum();
			int cols = sheet.getRow(0).getLastCellNum();
			for (int i = 1; i <= rows; i++) {
				map = new LinkedHashMap<>();
				for (int j = 0; j < cols; j++) {
					String key = sheet.getRow(0).getCell(j).getStringCellValue();
					DataFormatter formatter = new DataFormatter();
					String value = formatter.formatCellValue(sheet.getRow(i).getCell(j));
					map.put(key, value);
				}
				list.add(map);
			}
		} catch (FileNotFoundException e) {
			//throw new InvalidPathForExcelException("Excel file you trying to read is not found");
		} catch (IOException e) {
			// throw new FrameworkException("Some IO Exception happened while reading excel
			// file");
		}
		return list;
	}
}
