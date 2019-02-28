import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class GeneratorUtils {

    public static final Random RANDOM = new Random();

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static final String[] HEADERS = {
            "Имя",
            "Фамилия",
            "Отчество",
            "Возраст",
            "Пол",
            "Дата рождения",
            "Инн",
            "Почтовый индекс",
            "Страна",
            "Область",
            "Город",
            "Улица",
            "Дом",
            "Квартира"
    };

    private GeneratorUtils(){}

    public static String generateInnForRegion(String region){
        char[] innChars = new char[12];

        innChars[0] = region.charAt(0);
        innChars[1] = region.charAt(1);
        for(int i = 2 ; i < 10; i++){
            innChars[i] = Character.forDigit(RANDOM.nextInt(10), 10);
        }

        innChars[10] = Character.forDigit(((7 * innChars[0] +
                                            2 * innChars[1] +
                                            4 * innChars[2] +
                                            10 * innChars[3] +
                                            3 * innChars[4] +
                                            5 * innChars[5] +
                                            9 * innChars[6] +
                                            4 * innChars[7] +
                                            6 * innChars[8] +
                                            8 * innChars[9]) % 11) % 10, 10);
        innChars[11] = Character.forDigit(((3 * innChars[0] +
                                            7 * innChars[1] +
                                            2 * innChars[2] +
                                            4 * innChars[3] +
                                            10 * innChars[4] +
                                            3 * innChars[5] +
                                            5 * innChars[6] +
                                            9 * innChars[7] +
                                            4 * innChars[8] +
                                            6 * innChars[9] +
                                            8 * innChars[10]) % 11) % 10, 10);

        return new String(innChars);
    }

    public static String getRandomLineFromFile(String fileName) throws IOException {
        URL resourceUrl = GeneratorUtils.class.getClassLoader().getResource(fileName);

        if(resourceUrl != null){
            List<String> names = Files.readAllLines(Paths.get(new File(resourceUrl.getFile()).getAbsolutePath()));
            return names.get(RANDOM.nextInt(names.size()));
        }

        return null;
    }

    public static void saveDataToFile(byte[] data, String filePath) throws Exception {
        FileOutputStream out = new FileOutputStream(filePath);
        out.write(data);
        out.close();
    }

    public static LocalDate generateBirthDate(){
        int year = 1950 + GeneratorUtils.RANDOM.nextInt(50);
        int month = 1 + GeneratorUtils.RANDOM.nextInt(12);
        int day;

        if(month == 2){
            day = 1 + GeneratorUtils.RANDOM.nextInt(28);
        } else {
            day = 1 + GeneratorUtils.RANDOM.nextInt(30);
        }

        return LocalDate.of(year, month, day);
    }
}
