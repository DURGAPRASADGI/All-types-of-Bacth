package com.example.Batch.config;
import org.apache.poi.ss.usermodel.*;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.Resource;

import com.example.Batch.model.Employee;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ExcelItemReader implements ItemReader<Employee> {

    private final Resource resource;
    private Iterator<Row> rowIterator;

    public ExcelItemReader(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Employee read() throws Exception {
        if (rowIterator == null) {
            initialize(); // Initialize the iterator and skip the header row
        }

        if (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            return parseRow(row);
        } else {
            return null; // End of file
        }
    }

    // Initialize the row iterator and skip the header row
    private void initialize() throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet

            this.rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next(); // Skip the header row
            }
        }
    }

    // Parse a single row into an Employee object
    private Employee parseRow(Row row) {
        int id = (int) row.getCell(0).getNumericCellValue();
        String firstName = row.getCell(1).getStringCellValue();
        String lastName = row.getCell(2).getStringCellValue();
        String email = row.getCell(3).getStringCellValue();

        return new Employee(id, firstName, lastName, email);
    }
}
