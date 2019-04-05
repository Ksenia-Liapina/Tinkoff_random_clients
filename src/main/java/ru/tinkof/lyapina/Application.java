package ru.tinkof.lyapina;

import ru.tinkof.lyapina.generator.ApiGenerator;
import ru.tinkof.lyapina.generator.ExcelGenerator;
import ru.tinkof.lyapina.generator.IGenerator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class Application {

    public static void main(String[] args) {
        IGenerator generator = new ApiGenerator("https://randomuser.me/api/");

        boolean isGenerated = false;
        try {
            generator.generate();
            isGenerated = true;
        }
        catch (FileNotFoundException e){
            System.err.println("Не удалось сохранить данные в файл");
            System.err.println(e.getMessage());
        }
        catch (MalformedURLException e){
            System.err.println("Не удалось загрузить данные из api");
            System.err.println(e.getMessage());
        }
        catch (IOException e){
            System.err.println("Произошла ошибка при запросе пользователя по сети (используя api). Сгенерируем файл " +
                               "по статическим данным.");
            System.err.println(e.getMessage());
        }
        catch (Exception e){
            System.err.println("Неизвестная ошибка");
            System.err.println(e.getMessage());
        }
        finally {
            if(!isGenerated){
                generator = new ExcelGenerator("task.xls");
                try {
                    generator.generate();
                }
                catch (Exception e) {
                    System.err.println("Ошибка загрузки в Excel генератором.");
                    System.err.println(e.getMessage());
                }
            }
        }
    }

}
