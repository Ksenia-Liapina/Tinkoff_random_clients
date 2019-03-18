package ru.tinkof.lyapina.generator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import ru.tinkof.lyapina.utils.ExcelUtils;
import ru.tinkof.lyapina.utils.GeneratorUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;

public class ExcelGenerator implements IGenerator {

    private final File excelFile;

    public ExcelGenerator(String excelFilePath) {
        this.excelFile = new File(excelFilePath);
    }

    @Override
    public void generate() throws Exception {
        byte[] documentInBytes = generateExcelTable(GeneratorUtils.RANDOM.nextInt(30) + 1);

        GeneratorUtils.saveDataToFile(documentInBytes, excelFile.getAbsolutePath());

        System.out.println(String.format("Файл создан. Путь: %s", excelFile.getAbsolutePath()));
    }

    private byte[] generateExcelTable(int rowsCount) throws Exception {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = ExcelUtils.createSheetWithHeaders(workbook, "Users");

        int rowCount = 1;
        while (rowCount < rowsCount + 1) {
            Row userRow = sheet.createRow(rowCount);
            boolean isMale = GeneratorUtils.RANDOM.nextBoolean();
            String userName;
            String userSurName;
            String userMiddleName;

            if(isMale){
                userName = GeneratorUtils.getRandomLineFromFile("local/namesMale.txt");
                userSurName = GeneratorUtils.getRandomLineFromFile("local/surnamesMale.txt");
                userMiddleName = GeneratorUtils.getRandomLineFromFile("local/middleNamesMale.txt");
            } else {
                userName = GeneratorUtils.getRandomLineFromFile("local/namesFemale.txt");
                userSurName = GeneratorUtils.getRandomLineFromFile("local/surnamesFemale.txt");
                userMiddleName = GeneratorUtils.getRandomLineFromFile("local/middleNamesFemale.txt");
            }
            LocalDate birthDate = GeneratorUtils.generateBirthDate();
            int postIndex = 100000 + GeneratorUtils.RANDOM.nextInt(100000);

            userRow.createCell(0).setCellValue(userName);
            userRow.createCell(1).setCellValue(userSurName);
            userRow.createCell(2).setCellValue(userMiddleName);
            userRow.createCell(3).setCellValue(Period.between(birthDate, LocalDate.now()).getYears());
            userRow.createCell(4).setCellValue(isMale ? "М" : "Ж");
            userRow.createCell(5).setCellValue(birthDate.format(GeneratorUtils.DATE_FORMATTER));
            userRow.createCell(6).setCellValue(GeneratorUtils.generateInnForRegion("77"));
            userRow.createCell(7).setCellValue(postIndex);
            userRow.createCell(8).setCellValue(GeneratorUtils.getRandomLineFromFile("local/countries.txt"));
            userRow.createCell(9).setCellValue(GeneratorUtils.getRandomLineFromFile("local/regions.txt"));
            userRow.createCell(10).setCellValue(GeneratorUtils.getRandomLineFromFile("local/cities.txt"));
            userRow.createCell(11).setCellValue(GeneratorUtils.getRandomLineFromFile("local/streets.txt"));
            userRow.createCell(12).setCellValue(1 + GeneratorUtils.RANDOM.nextInt(150));
            userRow.createCell(13).setCellValue(1 + GeneratorUtils.RANDOM.nextInt(200));

            rowCount++;
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

}
