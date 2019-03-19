package ru.tinkof.lyapina.mapper;

import ru.tinkof.lyapina.domain.AddressEntity;
import ru.tinkof.lyapina.domain.PersonsEntity;
import ru.tinkof.lyapina.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static ru.tinkof.lyapina.utils.ExcelUtils.getHouseNumberFromStreet;
import static ru.tinkof.lyapina.utils.ExcelUtils.parseStringDate;

public class UserMapper {

    private UserMapper(){}

    public static PersonsEntity mapUserToEntityExisted(User user, PersonsEntity personsEntity) {
        personsEntity.setName(user.getName().getFirst());
        personsEntity.setSurname(user.getName().getLast());
        personsEntity.setGender(user.getGender().substring(0,1));
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

    public static PersonsEntity mapUserToEntity(User user) {
        PersonsEntity personsEntity = new PersonsEntity();
        personsEntity.setName(user.getName().getFirst());
        personsEntity.setSurname(user.getName().getLast());
        personsEntity.setGender(user.getGender().substring(0,1));
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
        personsEntity.setAddress(addressEntity);

        return addressEntity;
    }

    public static User mapEntityToUser(PersonsEntity personsEntity) {
        User user = new User();
        if(personsEntity.getAddress() != null){
            user.setNat(personsEntity.getAddress().getCountry());

            User.LocationInfo locationInfo = new User.LocationInfo();
            locationInfo.setPostcode(personsEntity.getAddress().getPostCode());
            locationInfo.setState(personsEntity.getAddress().getRegion());
            locationInfo.setCity(personsEntity.getAddress().getCity());
            locationInfo.setStreet(personsEntity.getAddress().getStreet());
            locationInfo.setFlat(personsEntity.getAddress().getFlat());
            user.setLocationInfo(locationInfo);


        }
        User.UserName userName = new User.UserName();
        userName.setFirst(personsEntity.getName());
        userName.setLast(personsEntity.getSurname());
        user.setName(userName);
        user.setGender(personsEntity.getGender());

        User.BirthInfo birthInfo = new User.BirthInfo();
        birthInfo.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").format(personsEntity.getBirthDay()));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(personsEntity.getBirthDay().getTime());
        birthInfo.setAge(Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR));
        user.setBirthInfo(birthInfo);

        return user;
    }
}
