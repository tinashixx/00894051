package com.cathay.spring.files.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.cathay.spring.files.entity.MyEntity;

public class UploadFileHelper {
	static String[] HEADERs = { "Id", "Title", "Description", "Published" };
	static String SHEET = "Tutorials";

	// check file Format
	public static boolean isFormat(MultipartFile file) {

		if (file.getContentType() != null && (file.getContentType().equals("text/plain")
				|| file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
			return true;
		}
		return false;
	}

	public static ByteArrayInputStream dataToExcel(List<MyEntity> testData) {
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}

			int rowIdx = 1;
			for (MyEntity tutorial : testData) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(tutorial.getId());
				row.createCell(1).setCellValue(tutorial.getTitle());
				row.createCell(2).setCellValue(tutorial.getDescription());
				row.createCell(3).setCellValue(tutorial.isPublished());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}

	public static List<MyEntity> excelOrTxtToEntities(InputStream is, String contentType) {
		List<MyEntity> entities = new ArrayList<>();

		if ("text/plain".equals(contentType)) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
				String line;
				boolean headerRow = true;
				while ((line = reader.readLine()) != null) {
					if (headerRow) {
						headerRow = false;
						continue;
					}

					String[] columns = line.split("\t");
					MyEntity entity = new MyEntity();
					entity.setId(Long.parseLong(columns[0]));
					entity.setTitle(columns[1]);
					entity.setDescription(columns[2]);
					entity.setPublished(Boolean.parseBoolean(columns[3]));

					entities.add(entity);
				}
			} catch (IOException e) {
				throw new RuntimeException("fail to parse txt file: " + e.getMessage());
			}
		} else {
			try {
				Workbook workbook = new XSSFWorkbook(is);

				Sheet sheet = workbook.getSheet(SHEET);
				Iterator<Row> rows = sheet.iterator();

				int rowNumber = 0;
				while (rows.hasNext()) {
					Row currentRow = rows.next();

					// skip header
					if (rowNumber == 0) {
						rowNumber++;
						continue;
					}

					Iterator<Cell> cellsInRow = currentRow.iterator();

					MyEntity entity = new MyEntity();

					int cellIdx = 0;
					while (cellsInRow.hasNext()) {
						Cell currentCell = cellsInRow.next();

						switch (cellIdx) {
						case 0:
							entity.setId((long) currentCell.getNumericCellValue());
							break;

						case 1:
							entity.setTitle(currentCell.getStringCellValue());
							break;

						case 2:
							entity.setDescription(currentCell.getStringCellValue());
							break;

						case 3:
							entity.setPublished(currentCell.getBooleanCellValue());
							break;

						default:
							break;
						}

						cellIdx++;
					}

					entities.add(entity);
				}

				workbook.close();
			} catch (IOException e) {
				throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
			}
		}

		return entities;
	}

}
