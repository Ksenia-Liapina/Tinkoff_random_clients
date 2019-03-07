package ru.tinkof.lyapina.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ru.tinkof.lyapina.utils.GeneratorUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Period;

import static com.itextpdf.text.PageSize.A4;

public class PdfGenerator implements IGenerator {

    private final File pdfFile;
    private final Font font;

    public PdfGenerator(String pdfFilePath) {
        this.pdfFile = new File(pdfFilePath);
        this.font = FontFactory.getFont(this.getClass().getClassLoader().getResource("OpenSans-Regular.ttf").getPath
                (), "cp1251");
    }

    @Override
    public void generate() throws Exception {
        //У генератора большие проблемы с кирилическими символами, поэтому я включила фантазию и написала транслитом текст в файле)))

        generatePDFTable(GeneratorUtils.RANDOM.nextInt(30) + 1);

        System.out.println(String.format("Файл создан. Путь: %s", pdfFile.getAbsolutePath()));
    }

    private void generatePDFTable(int rowsCount) throws Exception {
        Document document = new Document(A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

        document.open();

        PdfPTable table = new PdfPTable(GeneratorUtils.HEADERS.length);
        table.setWidthPercentage(100);

        addTableHeader(table);
        for(int i = 0 ; i < rowsCount ; i++){
            addRow(table);
        }

        document.add(table);
        document.close();
    }

    private void addTableHeader(PdfPTable table) throws UnsupportedEncodingException {
        for(String headerName : GeneratorUtils.HEADERS){
            PdfPCell cell = new PdfPCell(new Paragraph(GeneratorUtils.transliterate(headerName), font));
            cell.setNoWrap(false);
            table.addCell(cell);
        }
    }

    private void addRow(PdfPTable table) throws IOException {

        boolean isMale = GeneratorUtils.RANDOM.nextBoolean();
        LocalDate birthDate = GeneratorUtils.generateBirthDate();

        String userFileName = isMale ? "namesMale.txt" : "namesFemale.txt";
        String userFileSurName = isMale ? "surnamesMale.txt" : "surnamesFemale.txt";
        String userFileMiddleName = isMale ? "middleNamesMale.txt" : "middleNamesFemale.txt";

        table.addCell(getCellPhrase(GeneratorUtils.getRandomLineFromFile(userFileName), font));
        table.addCell(getCellPhrase(GeneratorUtils.getRandomLineFromFile(userFileSurName), font));
        table.addCell(getCellPhrase(GeneratorUtils.getRandomLineFromFile(userFileMiddleName), font));
        table.addCell(getCellPhrase(String.valueOf(Period.between(birthDate, LocalDate.now()).getYears()), font));
        table.addCell(getCellPhrase(isMale ? "М" : "Ж", font));
        table.addCell(getCellPhrase(birthDate.format(GeneratorUtils.DATE_FORMATTER), font));
        table.addCell(getCellPhrase(GeneratorUtils.generateInnForRegion("77"), font));
        table.addCell(getCellPhrase(String.valueOf(100000 + GeneratorUtils.RANDOM.nextInt(100000)), font));
        table.addCell(getCellPhrase(GeneratorUtils.getRandomLineFromFile("countries.txt"), font));
        table.addCell(getCellPhrase(GeneratorUtils.getRandomLineFromFile("regions.txt"), font));
        table.addCell(getCellPhrase(GeneratorUtils.getRandomLineFromFile("cities.txt"), font));
        table.addCell(getCellPhrase(GeneratorUtils.getRandomLineFromFile("streets.txt"), font));
        table.addCell(getCellPhrase(String.valueOf(1 + GeneratorUtils.RANDOM.nextInt(150)), font));
        table.addCell(getCellPhrase(String.valueOf(1 + GeneratorUtils.RANDOM.nextInt(200)), font));
    }

    private Phrase getCellPhrase(String text, Font font) throws UnsupportedEncodingException {
        return new Phrase(GeneratorUtils.transliterate(text), font);
    }


}
