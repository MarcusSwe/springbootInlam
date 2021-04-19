package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
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

    public Optional<UserDto> getProductById(String id){
        Optional<UserEntity> userIdEntity = userRepository.findByproductId(id);
        return userIdEntity.map(userEntity -> {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            return userDto;
        });
    }


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
            throw new BadRequestException("404 You dit not provide all info or wrong info");
        }

        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(userDetailsIn, userEntity);

        String userId = util.generatedHash(userDetailsIn.getName());

        userEntity.setProductId(userId.substring(3));

        UserEntity userEntityOut = userRepository.save(userEntity);
        UserDto userDtoOut = new UserDto();
        BeanUtils.copyProperties(userEntityOut, userDtoOut);
        return userDtoOut;
    }


    public Optional<UserDto> updateProduct(String id, UserDto userDto) {
        Optional<UserEntity> userIdEntity = userRepository.findByproductId(id);
        if (userIdEntity.isEmpty())
            return Optional.empty();
        return userIdEntity.map(userEntity -> {
            UserDto response = new UserDto();

            userEntity.setProductId(userDto.getProductId() != null ? util.generatedHash(userDto.getName()).substring(3) : userEntity.getProductId());

            userEntity.setName(userDto.getName() != null ? userDto.getName() : userEntity.getName());
            userEntity.setCost(userDto.getCost() >= 1 ? userDto.getCost() : userEntity.getCost());
            userEntity.setCategory(userDto.getCategory() != null ? userDto.getCategory() : userEntity.getCategory());


            UserEntity updatedUserEntity = userRepository.save(userEntity);
            BeanUtils.copyProperties(updatedUserEntity, response);
            return response;
        });
    }


    @Transactional
    public boolean removeProduct(String id) {
        long removedUsersCount = userRepository.deleteByproductId(id);
        return removedUsersCount > 0;
    }
}
