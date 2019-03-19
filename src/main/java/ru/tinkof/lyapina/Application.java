package ru.tinkof.lyapina;

import ru.tinkof.lyapina.generator.ApiGenerator;
import ru.tinkof.lyapina.generator.DatabaseGenerator;
import ru.tinkof.lyapina.generator.ExcelGenerator;
import ru.tinkof.lyapina.generator.IGenerator;

public class Application {

    public static void main(String[] args) throws Exception {
        IGenerator generator = new ApiGenerator("https://randomuser.me/api/");

        try {
            generator.generate();
        } catch (Exception e){
            System.out.println("Произошла ошибка при запросе пользователя по сети (используя api). Сгенерируем файл " +
                               "по статическим данным.");

            try {
                generator = new DatabaseGenerator("databaseGen.xls");
                generator.generate();
            } catch (Exception ex){
                generator = new ExcelGenerator("task.xls");
                generator.generate();
            }
        }

    }

}
