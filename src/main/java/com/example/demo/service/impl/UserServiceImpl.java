package com.example.demo.service.impl;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.shared.Util;
import com.example.demo.shared.dto.UserDto;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final Util util;

    public UserServiceImpl(UserRepository userRepository, Util util) {
        this.userRepository = userRepository;
        this.util = util;
    }

   /* public String getUser() {
        return "getUser";
    }*/

    /*public Optional<UserDto> getUserById(String id){
        Optional<UserEntity> userIdEntity = userRepository.findByUserId(id);
        return userIdEntity.map(userEntity -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            return userDto;
        }); // ovan gör man om optional UserEntity till optional UserDto..
    }*/


    @Override
    public List<UserDto> getProducts() {
        Iterable<UserEntity> userEntities = userRepository.findAll();
        ArrayList<UserDto> userDtos = new ArrayList<>();
        for (UserEntity userEntity : userEntities){
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            userDtos.add(userDto);
        }
        return userDtos;
    }


    public UserDto createProduct(UserDto userDetailsIn) {
        if (userDetailsIn.getName().equals("") ||
                ((userDetailsIn.getCost() < 1)) ||
                userDetailsIn.getCategory().equals("")
                ){
            throw new RuntimeException("You dit not provide all info or wrong info");
        }

        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(userDetailsIn, userEntity);

        String userId = util.generatedHash(userDetailsIn.getName());

        userEntity.setProductId(userId.substring(3)); // ta bort 3 första tecknen på user id som generas av mailen..special tecken som inte verkas gilla..

        UserEntity userEntityOut = userRepository.save(userEntity); // detta sparar till databasen..
        UserDto userDtoOut = new UserDto();
        BeanUtils.copyProperties(userEntityOut, userDtoOut);
        return userDtoOut;
    }

    /*
    public Optional<UserDto> updateUser(String id, UserDto userDto) { //userDto är Json
        Optional<UserEntity> userIdEntity = userRepository.findByUserId(id);
        if (userIdEntity.isEmpty())
            return Optional.empty(); //java kommer tolka det som ett optional UserDto automatiskt..
        return userIdEntity.map(userEntity -> {
            UserDto response = new UserDto();
           // BeanUtils.copyProperties(userDto, userEntity); // updaterar här..kopierar över data från Json till raden i databasen..så detta ändrar saker i databasen..
            // ändrar ovan..eftersom inputen kanske har null på vissa ställen.. tex man uppdaterar bara email då skickar man samtidigt med null på dem andra platserna..
            // istället kopierar över gamla entries så dem inte blir null ..

            userEntity.setEncryptedPassword(userDto.getPassword() != null ? BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(10)) : userEntity.getEncryptedPassword());

            userEntity.setUserId(userDto.getUserId() != null ? util.generatedHash(userDto.getEmail()).substring(3) : userEntity.getUserId());

            userEntity.setFirstName(userDto.getFirstName() != null ? userDto.getFirstName() : userEntity.getFirstName());
            userEntity.setLastName(userDto.getLastName() != null ? userDto.getLastName() : userEntity.getLastName());
            userEntity.setEmail(userDto.getEmail() != null ? userDto.getEmail() : userEntity.getEmail());


            UserEntity updatedUserEntity = userRepository.save(userEntity); //save har inte vi gjort..kommer från CrudRepository..
            BeanUtils.copyProperties(updatedUserEntity, response);
            return response;
        }); //userEntity är rad i databasen..
    }*/

    /*
    @Transactional
    public boolean deleteUser(String id) {
        long removedUsersCount = userRepository.deleteByUserId(id);
        return removedUsersCount > 0;
    }*/
}
