package ru.tinkof.lyapina.utils;

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

    public static byte[] createExcelFileForUsers(final String fileName, final List<User> users) throws IOException {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");
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

        int rowCount = 1;
        for(User user : users){
            Row userRow = sheet.createRow(rowCount);

            userRow.createCell(0).setCellValue(user.getName().getFirst());
            userRow.createCell(1).setCellValue(user.getName().getLast());
            userRow.createCell(2).setCellValue("");
            userRow.createCell(3).setCellValue(user.getBirthInfo().getAge());
            userRow.createCell(4).setCellValue(user.getGender());
            userRow.createCell(5).setCellValue(LocalDate.parse(user.getBirthInfo().getBirthDate())
                                                        .format(GeneratorUtils.DATE_FORMATTER));
            userRow.createCell(6).setCellValue("");
            userRow.createCell(7).setCellValue(user.getLocationInfo().getPostcode());
            userRow.createCell(8).setCellValue(user.getNat());
            userRow.createCell(9).setCellValue(user.getLocationInfo().getState());
            userRow.createCell(10).setCellValue(user.getLocationInfo().getCity());
            userRow.createCell(11).setCellValue(user.getLocationInfo().getStreet());
            userRow.createCell(12).setCellValue(user.getLocationInfo().getStreet());
            userRow.createCell(13).setCellValue(1 + GeneratorUtils.RANDOM.nextInt(200));

            rowCount++;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        return baos.toByteArray();
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
}
