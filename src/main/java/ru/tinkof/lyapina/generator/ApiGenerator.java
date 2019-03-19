package ru.tinkof.lyapina.generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.tinkof.lyapina.dao.AddressDAO;
import ru.tinkof.lyapina.dao.PersonsDAO;
import ru.tinkof.lyapina.domain.PersonsEntity;
import ru.tinkof.lyapina.mapper.UserMapper;
import ru.tinkof.lyapina.model.User;
import ru.tinkof.lyapina.model.UsersHolder;
import ru.tinkof.lyapina.utils.ExcelUtils;
import ru.tinkof.lyapina.utils.GeneratorUtils;
import ru.tinkof.lyapina.utils.UrlUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        this.saveToDB(usersHolder.getUsers());

        String filePath = new File(FILE_NAME).getAbsolutePath();

        GeneratorUtils.saveDataToFile(fileData, filePath);
        System.out.println(String.format("Файл создан. Путь: %s", filePath));
    }

    private void saveToDB(List<User> users){
        PersonsDAO personsDAO = new PersonsDAO();
        AddressDAO addressDAO = new AddressDAO();
        for(User user : users){
            PersonsEntity entity = personsDAO.findByFullName(
                    user.getName().getLast(),
                    user.getName().getFirst()
            );

            PersonsEntity personsEntity;
            if(entity == null){
                personsEntity = UserMapper.mapUserToEntity(user);
            } else {
                personsEntity = UserMapper.mapUserToEntityExisted(user, entity);
            }

            addressDAO.persist(personsEntity.getAddress());
            personsDAO.persist(personsEntity);
        }
    }
}
