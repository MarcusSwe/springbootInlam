package com.example.demo.repository;

import com.example.demo.repository.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByproductId(String id); //ska matcha kolumn namnet i tabellen och då under UserEntity.java..alltså "findBy"+"UserId" i ett namn tex..
    Long deleteByproductId(String id);


} // extends .. får man tillgång till CrudRepository
