package ru.tinkof.lyapina.mapper;

import ru.tinkof.lyapina.domain.AddressEntity;
import ru.tinkof.lyapina.domain.PersonsEntity;
import ru.tinkof.lyapina.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static ru.tinkof.lyapina.utils.ExcelUtils.getHouseNumberFromStreet;
import static ru.tinkof.lyapina.utils.ExcelUtils.parseStringDate;

public class UserMapper {

    private UserMapper(){}

    public static PersonsEntity mapUserToEntity(User user) {
        PersonsEntity personsEntity = new PersonsEntity();
        personsEntity.setName(user.getName().getFirst());
        personsEntity.setSurname(user.getName().getLast());
        personsEntity.setGender(user.getGender());
        try {
            personsEntity.setBirthDay(new SimpleDateFormat("yyyy-MM-dd").parse(
                    parseStringDate(user.getBirthInfo().getBirthDate()))
            );
        }
        catch (ParseException e) {
            System.out.println("Не удалось сохранить дату");
        }
        personsEntity.setAddress(mapUserAddressInfoToEntity(user, personsEntity));

        return personsEntity;
    }

    private static AddressEntity mapUserAddressInfoToEntity(User user, PersonsEntity personsEntity){
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setPostCode(user.getLocationInfo().getPostcode());
        addressEntity.setRegion(user.getLocationInfo().getState());
        addressEntity.setStreet(user.getLocationInfo().getStreet());
        addressEntity.setCountry(user.getNat());
        addressEntity.setHouse(Integer.parseInt(getHouseNumberFromStreet(user.getLocationInfo().getStreet())));
        addressEntity.setCity(user.getLocationInfo().getStreet());
        addressEntity.setFlat(user.getLocationInfo().getFlat());
        addressEntity.setPersonsEntity(personsEntity);

        return addressEntity;
    }
}
