package com.example.demo.controller;

import com.example.demo.model.request.UserDetailsRequestModel;
import com.example.demo.model.response.UserResponseModel;
import com.example.demo.service.UserService;
import com.example.demo.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NoContentException;

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
   public List<UserResponseModel> getUsers() {
        List<UserDto> userDtos = userService.getProducts();
        ArrayList<UserResponseModel> responseList = new ArrayList<>();

       if (userDtos.isEmpty()) {
           throw new NoContentException("204 No Content");
       } else {
           for (UserDto userDto : userDtos) {
               UserResponseModel responseModel = new UserResponseModel();
               BeanUtils.copyProperties(userDto, responseModel);
               responseList.add(responseModel);
           }
           return responseList;
       }
    }


  @GetMapping("/{id}")
  public UserResponseModel getUser(@PathVariable String id) {
        UserResponseModel responseModel = new UserResponseModel();
        Optional<UserDto> optionalUserDto = userService.getProductById(id);
        if (optionalUserDto.isPresent()) {
            UserDto userDto = optionalUserDto.get();
            BeanUtils.copyProperties(userDto, responseModel);
            return responseModel;
        }
        throw new NotFoundException("404 Not Found");
        //return userService.getUser();
    }

    @PostMapping
    public UserResponseModel createProduct(@RequestBody UserDetailsRequestModel userDetailsModel) {
        UserDto userDtoIn = new UserDto();
        BeanUtils.copyProperties(userDetailsModel, userDtoIn);

        if (userDetailsModel.getName().equals("") || userDetailsModel.getCategory().equals("") || userDetailsModel.getCost()<1) {
            throw new BadRequestException("400 Bad Request");
        } else {
            UserDto userDtoOut = userService.createProduct(userDtoIn);
            UserResponseModel response = new UserResponseModel();
            BeanUtils.copyProperties(userDtoOut, response);
            return response;
        }
    }


    @PutMapping("/{id}")
    public UserResponseModel updateProduct(@PathVariable String id, @RequestBody UserDetailsRequestModel requestData) {
        // copy json to dto in, requestData är Json
        UserDto userDtoIn = new UserDto();
        BeanUtils.copyProperties(requestData, userDtoIn);

        // pass dto in to service layer
        Optional<UserDto> userDtoOut = userService.updateProduct(id, userDtoIn);
        if (userDtoOut.isEmpty()) {
            throw new NotFoundException("404 Not Found");
        }


        UserDto userDto = userDtoOut.get();
        UserResponseModel responseModel = new UserResponseModel();
        BeanUtils.copyProperties(userDto, responseModel);
        return responseModel;
    }


    @DeleteMapping("/{id}")
    public String removeProduct(@PathVariable String id) {
        boolean deleted = userService.removeProduct(id);
        if (deleted) {
            return "";
        }
        throw new NotFoundException("404 Not Found");

    }
}
