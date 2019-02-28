public class Application {

    public static void main(String[] args) throws Exception {
        IGenerator generator = new ExcelGenerator("task.xls");
        generator.generate();

        generator = new PdfGenerator("task.pdf");
        generator.generate();
    }

}
