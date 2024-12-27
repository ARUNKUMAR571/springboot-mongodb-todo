package com.example.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.model.ToDoDTO;

public interface TodoRepository extends MongoRepository<ToDoDTO, String> {

    @Query("{'todo': ?0}")
    Optional<ToDoDTO> findByTodo(String todo);
}
