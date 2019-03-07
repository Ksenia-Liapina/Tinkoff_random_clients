package ru.tinkof.lyapina.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.tinkof.lyapina.model.UsersHolder;
import ru.tinkof.lyapina.utils.ExcelUtils;
import ru.tinkof.lyapina.utils.GeneratorUtils;
import ru.tinkof.lyapina.utils.UrlUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ApiGenerator implements IGenerator {

    private final String FILE_NAME = "apiGenerated.xls";

    private final String apiUrl;
    private final ObjectMapper mapper;

    public ApiGenerator(String apUrl) {
        this.apiUrl = apUrl;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void generate() throws Exception {
        final int usersCount = GeneratorUtils.RANDOM.nextInt(30) + 1;
        final Map<String, String> params = new HashMap<String, String>(){{
            put("inc", "gender,name,location,nat,dob");
            put("results", String.valueOf(usersCount));
            put("noinfo", null);
        }};

        UsersHolder usersHolder = this.mapper.readValue(UrlUtils.createUrlWithParams(this.apiUrl, params),
                                                        UsersHolder.class);

        byte[] fileData = ExcelUtils.createExcelFileForUsers(usersHolder.getUsers());

        String filePath = new File(FILE_NAME).getAbsolutePath();

        GeneratorUtils.saveDataToFile(fileData, filePath);
        System.out.println(String.format("Файл создан. Путь: %s", filePath));
    }
}
