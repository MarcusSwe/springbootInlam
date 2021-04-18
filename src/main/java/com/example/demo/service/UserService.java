package com.example.demo.service;

import com.example.demo.shared.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto createProduct(UserDto productDetails);

  /*  Optional<UserDto> updateProduct(String id, UserDto userDto);

    boolean deleteProduct(String id);

    Optional<UserDto> getProductById(String id);*/

    List<UserDto> getProducts();


}
