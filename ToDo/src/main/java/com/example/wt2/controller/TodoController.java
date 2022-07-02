package com.example.wt2.controller;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wt2.authcauthz.WrapperSecUtil;
import com.example.wt2.models.TodoItems;
import com.example.wt2.models.User;
import com.example.wt2.repositories.TodoRepository;
import com.example.wt2.repositories.UserRepository;

@RestController
//Alle Elemente der Klasse mappen damit erst über "/api" und danach methodengesteuert über die Mapping-Angabe an der Methode
@RequestMapping ("/api")
//erlaubt CrossOrigin Zugriffe, sodass wir mit dem Angular Frontend zugreifen können
@CrossOrigin("*")
public class TodoController {

	@Autowired //injected die Repository Instanz
	TodoRepository todoRepository;
	@Autowired
	 WrapperSecUtil WrapperSecUtil;
	@Autowired
	UserRepository userRepository;
	
	private static final transient Logger log = LoggerFactory.getLogger(TodoController.class);
	
	
	@GetMapping("/todos")
    public Page<TodoItems> getTodos(@RequestParam(name = "c", defaultValue = "0") int page, @RequestParam(name = "x", defaultValue = "30") int pageSize) {
        if(WrapperSecUtil.getSubject().hasRole("ADMIN")){
            return todoRepository.findAll(PageRequest.of(page, pageSize));
        }
        Optional<User> optUser = userRepository.findById(WrapperSecUtil.getPrincipal());
        if (optUser.isEmpty()) {
            return null; 
        }
        return todoRepository.findAllByOwner(PageRequest.of(page, pageSize), optUser.get());
    }
	
	/*@PostMapping("/todos")
    public TodoItems saveTodo(@RequestBody TodoItems incomingTodo) {
        TodoItems todo = new TodoItems();
        todo.setTitle(incomingTodo.getTitle());
        todo.setCompleted(false);
        Optional<User> optUser = userRepository.findById(WrapperSecUtil.getPrincipal());
        todo.setOwner(optUser.get());
        return this.todoRepository.save(todo); 
    } */
	
	@PutMapping("/{id}")
    public ResponseEntity<Object> updateTodo(@PathVariable("id") Long todoId, @RequestBody TodoItems incomingTodo) {
        Optional<TodoItems> TodosById = todoRepository.findById(todoId);
        if(TodosById.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!WrapperSecUtil.getPrincipal().equals(TodosById.get().getOwner().getUsername()) && !WrapperSecUtil.getSubject().hasRole("ADMIN")){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        TodoItems newTodo = TodosById.get();
        newTodo.setTitle(incomingTodo.getTitle());
        return new ResponseEntity<>(this.todoRepository.save(newTodo), HttpStatus.OK);
    }
	
	//@RequestBody mappt den HTTPRequest body direkt auf ein ToDo Objekt => automatische deserialiserung 
		//ermöglicht das erstellen eines ToDo Objekt per HTTP Request
		 @PostMapping("/todos")
		public TodoItems createTodo(@RequestBody TodoItems todo) {
			todo.setCompleted(false);
			Optional<User> optUser = userRepository.findById(WrapperSecUtil.getPrincipal());
	        todo.setOwner(optUser.get());
			return todoRepository.save(todo);
		}
		 //Löscht die angebene ToDo, falls der Nutzer die erforderliche Berechtigung hat
		 @DeleteMapping("/{id}") //@PathVariable binded {id} aud der URI an den Wert id
		    public ResponseEntity<?> deleteTodo(@PathVariable("id") Long todoId) {
		        Optional<TodoItems> optTodo = this.todoRepository.findById(todoId);
		        if(optTodo.isEmpty()){
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		            // Wenn die Todo nicht dem angegebenen Nutzer gehört oder der Nutzer die Admin Rolle hat, wird die Anfrage abgelehnt
		        } else if(!optTodo.get().getOwner().getUsername().equals(WrapperSecUtil.getPrincipal())  && !WrapperSecUtil.getSubject().hasRole("ADMIN")){
		            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		        }
		        this.todoRepository.deleteById(todoId);
		        return new ResponseEntity<>(HttpStatus.OK);
		 }
	
		 //GET Methode, die die per id angegebene ToDo zurückgibt, falls der Nutzer die Berechtigung dazu hat 
		 @GetMapping("/{id}")
		    public ResponseEntity<?> getTodo(@PathVariable("id") Long id) {
		        Optional<TodoItems> optTodo = this.todoRepository.findById(id);
		        if(optTodo.isEmpty()){
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
		        if(!optTodo.get().getOwner().getUsername().equals(WrapperSecUtil.getPrincipal())  && !WrapperSecUtil.getSubject().hasRole("ADMIN")){
		            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		        }
		        return new ResponseEntity<>(optTodo.get(), HttpStatus.OK);
		    }
	
	
		 	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/*@GetMapping("/todos")
	public @ResponseBody Iterable<TodoItems> getAll() {
		if(WrapperSecUtil.getSubject().hasRole("Admin")) {
			Iterable<TodoItems> todoList = todoRepository.findAll();
			return todoRepository.findAll();
		}
		Optional<User> optUser = userRepository.findById(WrapperSecUtil.getPrincipal());
		if(optUser.isEmpty()) {
			return null;
		}
		return todoRepository.findAllByOwner(PageRequest.of(0,20), optUser.get());
		/*	Iterable<TodoItems> todoList = todoRepository.findAll();
		return todoRepository.findAll(); */
	}
	
	
	//@RequestBody mappt den HTTPRequest body direkt auf ein ToDo Objekt => automatische deserialiserung */
	//ermöglicht das erstellen eines ToDo Objekt per HTTP Request
	/* @PostMapping("/todos")
	public TodoItems createTodo(@RequestBody TodoItems todo) {
		todo.setCompleted(false);
		return todoRepository.save(todo);
	}
	/*gibt das ToDo Objekt mit der angebenen ID zurück
	 * @PathVariable binded {id} aud der URI an den Sring id
	 * findById wird durch Spring repository bereitgestellt */
	/*@GetMapping(value="/todos/{id}")
	public ResponseEntity<TodoItems> getTodoById(@PathVariable("id") long id) {
		
		
		return todoRepository.findById(id).map(
				todo -> ResponseEntity.ok().body(todo)).orElse(ResponseEntity.notFound().build());
	}
	@PutMapping(value="/todos/{id}")
	public ResponseEntity<TodoItems> updateTodo (@PathVariable("id") long id,@RequestBody TodoItems todo) {
		return todoRepository.findById(id).map(todoData -> {
			todoData.setTitle(todo.getTitle());
			todoData.setCompleted(todo.isCompleted());
			TodoItems updateTodo = todoRepository.save(todoData);
			return ResponseEntity.ok().body(updateTodo);}).orElse(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping(value="todos/{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable ("id") long id) {
		return todoRepository.findById(id).map(todo -> {
			todoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	} */

