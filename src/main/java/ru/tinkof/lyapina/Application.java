package ru.tinkof.lyapina;

import ru.tinkof.lyapina.generator.ApiGenerator;
import ru.tinkof.lyapina.generator.ExcelGenerator;
import ru.tinkof.lyapina.generator.IGenerator;

public class Application {

    public static void main(String[] args) throws Exception {
        IGenerator generator = new ApiGenerator("https://randomuser.me/api/");

        generator.generate();
        /*try {
            generator.generate();
        } catch (Exception e){
            System.out.println("Произошла ошибка при запросе пользователя по сети (используя api). Сгенерируем файл " +
                               "по статическим данным.");

            generator = new ExcelGenerator("task.xls");
            generator.generate();
        }*/
    }

}
