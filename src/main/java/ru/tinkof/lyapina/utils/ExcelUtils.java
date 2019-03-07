package ru.tinkof.lyapina.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import ru.tinkof.lyapina.model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.apache.poi.ss.usermodel.CellStyle.ALIGN_CENTER;

public class ExcelUtils {

    private ExcelUtils(){}

    public static byte[] createExcelFileForUsers(final List<User> users) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = createSheetWithHeaders(workbook, "Users");

        int rowCount = 1;
        for(User user : users){
            Row userRow = sheet.createRow(rowCount);

            userRow.createCell(0).setCellValue(user.getName().getFirst());
            userRow.createCell(1).setCellValue(user.getName().getLast());
            userRow.createCell(2).setCellValue("Н/Д");
            userRow.createCell(3).setCellValue(user.getBirthInfo().getAge());
            userRow.createCell(4).setCellValue(user.getGender());
            userRow.createCell(5).setCellValue(parseStringDate(user.getBirthInfo().getBirthDate()));
            userRow.createCell(6).setCellValue("Н/Д");
            userRow.createCell(7).setCellValue(user.getLocationInfo().getPostcode());
            userRow.createCell(8).setCellValue(user.getNat());
            userRow.createCell(9).setCellValue(user.getLocationInfo().getState());
            userRow.createCell(10).setCellValue(user.getLocationInfo().getCity());
            userRow.createCell(11).setCellValue(user.getLocationInfo().getStreet());
            userRow.createCell(12).setCellValue(getHouseNumberFromStreet(user.getLocationInfo().getStreet()));
            userRow.createCell(13).setCellValue(1 + GeneratorUtils.RANDOM.nextInt(200));

            rowCount++;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        return baos.toByteArray();
    }

    public static Sheet createSheetWithHeaders(Workbook workbook, final String sheetName){
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth(40);

        CellStyle cellStyleHeader = workbook.createCellStyle();
        cellStyleHeader.setWrapText(true);
        cellStyleHeader.setAlignment(ALIGN_CENTER);
        cellStyleHeader.setVerticalAlignment(ALIGN_CENTER);
        cellStyleHeader.setIndention((short) 1);

        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyleHeader.setFont(font);

        fillHeaders(sheet, cellStyleHeader);

        return sheet;
    }

    public static void fillHeaders(Sheet sheet, CellStyle cellStyleHeader) {
        Row headerForNames = sheet.createRow(0);
        int headerCellInd = 0;
        for(String headerName : GeneratorUtils.HEADERS){
            Cell headerCell = headerForNames.createCell(headerCellInd);
            headerCell.setCellStyle(cellStyleHeader);
            headerCell.setCellValue(headerName);

            headerCellInd++;
        }
    }


    private static String getHouseNumberFromStreet(String street){
        String[] splitted = street.split(" ");

        for(String streetPart : splitted){
            if(StringUtils.isNumeric(streetPart)){
                return streetPart;
            }
        }

        return "Н/Д";
    }

    private static String parseStringDate(String dateStr){
        String[] splitted = dateStr.split("T");
        if(splitted.length < 2){
            return dateStr;
        }

        return splitted[0];
    }
}
