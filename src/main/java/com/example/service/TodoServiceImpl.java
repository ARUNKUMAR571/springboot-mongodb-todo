package com.example.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.TodoCollectionException;
import com.example.model.ToDoDTO;
import com.example.repository.TodoRepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class TodoServiceImpl implements ToDoService {

    @Autowired
    private TodoRepository todoRepo;

    @Override
    public void createTodo(ToDoDTO todo) throws ConstraintViolationException, TodoCollectionException {
        Optional<ToDoDTO> todoOptional = todoRepo.findByTodo(todo.getTodo());
        if (todoOptional.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.todoAlreadyExists());
        } else {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todo.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
        }
    }

	@Override
	public List<ToDoDTO> getAllTodos() {
		List<ToDoDTO> todos = todoRepo.findAll();
		if(todos.size()>0) {
			return todos;
		}else {
			return new ArrayList<ToDoDTO>();
		}
		
	}

	@Override
	public ToDoDTO getSingleTodo(String id) throws TodoCollectionException {
		Optional<ToDoDTO> optionalTodo = todoRepo.findById(id);
		if(!optionalTodo.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.notFoundException(id));
		}else {
			return optionalTodo.get();
		}
	}

	@Override
	public void deleteTodo(String id) throws TodoCollectionException {
		Optional<ToDoDTO> findById = todoRepo.findById(id);
		if(!findById.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.notFoundException(id));
		}else {
			todoRepo.deleteById(id);
		}	
	}
	
	 public ToDoDTO updateTodo(String id,ToDoDTO todo) throws TodoCollectionException {
       Optional<ToDoDTO> findById = todoRepo.findById(id);
       if (findById.isPresent()) {
           ToDoDTO updateTodo = findById.get();
           updateTodo.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : updateTodo.getCompleted());
           updateTodo.setTodo(todo.getTodo() != null ? todo.getTodo() : updateTodo.getTodo());
           updateTodo.setDescription(todo.getDescription() != null ? todo.getDescription() : updateTodo.getDescription());
           updateTodo.setUpdatedAt(new Date());
           return todoRepo.save(updateTodo);
           
       } else {
           throw new TodoCollectionException(TodoCollectionException.notFoundException(id));
       }
   }
	
	
    
  
}
