package com.example.service;

import java.util.List;

import com.example.exception.TodoCollectionException;
import com.example.model.ToDoDTO;

import jakarta.validation.ConstraintViolationException;

public interface ToDoService {

    void createTodo(ToDoDTO todo) throws ConstraintViolationException, TodoCollectionException;
    
    public List<ToDoDTO> getAllTodos();
    
    public ToDoDTO getSingleTodo(String id) throws TodoCollectionException;
    
    public void deleteTodo(String id) throws TodoCollectionException;
    
    public ToDoDTO updateTodo(String id,ToDoDTO todo) throws TodoCollectionException;
}
