package com.example.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.exception.TodoCollectionException;
import com.example.model.ToDoDTO;
import com.example.repository.TodoRepository;
import com.example.service.ToDoService;

import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("/")
public class ToDoController {

    @Autowired
    private TodoRepository todoRepo;

    @Autowired
    private ToDoService todoService;

    @GetMapping("/getAllTodos")
    public ResponseEntity<?> getAllTodos() {
        List<ToDoDTO> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos,todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createToDo")
    public ResponseEntity<?> createTodo(@RequestBody ToDoDTO todo) {
        try {
            todoService.createTodo(todo);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getSingleTodo/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id) {
    	try {
			return new ResponseEntity<>(todoService.getSingleTodo(id),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
    }
    	
    	
    @PutMapping("/updateTodo/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") String id, @RequestBody ToDoDTO todo) {
      try {
    	  ToDoDTO updateTodo = todoService.updateTodo(id, todo);
    	  return new ResponseEntity<>(updateTodo,HttpStatus.OK);
      }catch(Exception e) {
    	  return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
      }
    }

    @DeleteMapping("/deleteTodo/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String id) {
        try {
            todoService.deleteTodo(id);
            return new ResponseEntity<>("Todo deleted with id: " + id, HttpStatus.OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
