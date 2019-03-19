package ru.tinkof.lyapina.generator;

import ru.tinkof.lyapina.dao.PersonsDAO;
import ru.tinkof.lyapina.domain.PersonsEntity;
import ru.tinkof.lyapina.mapper.UserMapper;
import ru.tinkof.lyapina.utils.ExcelUtils;
import ru.tinkof.lyapina.utils.GeneratorUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseGenerator implements IGenerator {

    private String fileName;

    public DatabaseGenerator(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void generate() throws Exception {
        final int usersCount = GeneratorUtils.RANDOM.nextInt(30) + 1;

        List<PersonsEntity> personsEntityList = new PersonsDAO().findAllByCount(usersCount);

        if(personsEntityList == null || personsEntityList.size() == 0){
            throw new Exception("Данных нет");
        }

        byte[] fileData = ExcelUtils.createExcelFileForUsers(
                personsEntityList
                        .stream()
                        .map(UserMapper::mapEntityToUser)
                        .collect(Collectors.toList())
        );

        String filePath = new File(this.fileName).getAbsolutePath();

        GeneratorUtils.saveDataToFile(fileData, filePath);
        System.out.println(String.format("Файл создан. Путь: %s", filePath));
    }
}
