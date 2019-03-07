package ru.tinkof.lyapina.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.tinkof.lyapina.model.UsersHolder;
import ru.tinkof.lyapina.utils.ExcelUtils;
import ru.tinkof.lyapina.utils.GeneratorUtils;

import java.io.File;
import java.net.URL;

public class ApiGenerator implements IGenerator {

    private final String FILE_NAME = "apiGenerated.xls";

    private final String API_URL;

    public ApiGenerator(String apUrl) {
        API_URL = apUrl;
    }

    @Override
    public void generate() throws Exception {
        final int usersCount = GeneratorUtils.RANDOM.nextInt(30) + 1;

        ObjectMapper mapper = new ObjectMapper();
        UsersHolder usersHolder = mapper.readValue(new URL(
                String.format("%s?inc=%s&results=%d",
                              API_URL,
                              "gender, name, location, nat, dob",
                              usersCount)
        ), UsersHolder.class);

        byte[] fileData = ExcelUtils.createExcelFileForUsers(FILE_NAME, usersHolder.getUsers());
        GeneratorUtils.saveDataToFile(fileData, new File(FILE_NAME).getAbsolutePath());
    }
}
