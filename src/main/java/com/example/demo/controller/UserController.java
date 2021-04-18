package com.example.demo.controller;

import com.example.demo.model.request.UserDetailsRequestModel;
import com.example.demo.model.response.UserResponseModel;
import com.example.demo.service.UserService;
import com.example.demo.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("products") // http://localhost:8080/products
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


   @GetMapping // alla users
   public List<UserResponseModel> getUsers() { // id som kommer in från getMapping ovan mappas in till pathvariable id i parametern!
        List<UserDto> userDtos = userService.getProducts();
        ArrayList<UserResponseModel> responseList = new ArrayList<>();
        for (UserDto userDto : userDtos) {
            UserResponseModel responseModel = new UserResponseModel();
            BeanUtils.copyProperties(userDto, responseModel);
            responseList.add(responseModel);
        }
        return responseList;
    }


  @GetMapping("/{id}") // http://localhost:8080/users/[B@3ec8351b
  public UserResponseModel getUser(@PathVariable String id) { // id som kommer in från getMapping ovan mappas in till pathvariable id i parametern!
        UserResponseModel responseModel = new UserResponseModel();
        Optional<UserDto> optionalUserDto = userService.getProductById(id);
        if (optionalUserDto.isPresent()) {
            UserDto userDto = optionalUserDto.get();
            BeanUtils.copyProperties(userDto, responseModel);
            return responseModel;
        }
        throw new RuntimeException("No product with id " + id);
        //return userService.getUser();
    }

    @PostMapping
    public UserResponseModel createProduct(@RequestBody UserDetailsRequestModel userDetailsModel) {
        // copy json to dto in
        UserDto userDtoIn = new UserDto();
        BeanUtils.copyProperties(userDetailsModel, userDtoIn);

        // pass dto in to service layer
        UserDto userDtoOut = userService.createProduct(userDtoIn);

        // copy dto out from service layer to repsonse
        UserResponseModel response = new UserResponseModel();
        BeanUtils.copyProperties(userDtoOut, response);
        return response;
    }

    /*
    @PutMapping("/{id}")
    public UserResponseModel updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel requestData) {
        // copy json to dto in, requestData är Json
        UserDto userDtoIn = new UserDto();
        BeanUtils.copyProperties(requestData, userDtoIn);

        // pass dto in to service layer
        Optional<UserDto> userDtoOut = userService.updateUser(id, userDtoIn);
        if (userDtoOut.isEmpty()) {
            throw new RuntimeException("Not found");
        }


        UserDto userDto = userDtoOut.get();
        UserResponseModel responseModel = new UserResponseModel();
        BeanUtils.copyProperties(userDto, responseModel);
        return responseModel;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return "";
        }
        throw new RuntimeException("No user with id " + id);

    } */
}
