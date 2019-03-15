package ru.tinkof.lyapina;

public class Application {

    public static void main(String[] args) {
        try {
            IGenerator generator = new ExcelGenerator("task.xls");
            generator.generate();

            generator = new PdfGenerator("task.pdf");
            generator.generate();
        } catch (Exception e){
            System.out.println("Ошибка генерации отчета");
        }

    }

}
