package com.CrmService.controller;

import java.util.List;

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

import com.CrmService.dto.CategoryDto;
import com.CrmService.dto.Message;
import com.CrmService.service.CategoryService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/category")
@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {

	private final CategoryService categoryservice;

	
	public CategoryController(CategoryService categoryservice) {
		super();
		this.categoryservice = categoryservice;
	}
	@PostMapping("/addCategory")
	public ResponseEntity<Message<CategoryDto>>AddCategory(@RequestBody CategoryDto request) {
		log.info("In usercontroller login() with request:{}",request);
		Message<CategoryDto> message=categoryservice.AddCategory(request);
		HttpStatus httpStatus=HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@PutMapping("/updateCategory")
	public ResponseEntity<Message<CategoryDto>>UpdateCategory(@RequestBody CategoryDto request) {
		log.info("In usercontroller login() with request:{}",request);
		Message<CategoryDto> message=categoryservice.UpdateCategory(request);
		HttpStatus httpStatus=HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
   }
	@DeleteMapping("/deleteCategory/{cId}")
	public ResponseEntity<Message<CategoryDto>>DeleteCategory(@PathVariable int cId) {
		log.info("In usercontroller login() with request:{}",cId);
		Message<CategoryDto> message=categoryservice.DeleteCategory(cId);
		HttpStatus httpStatus=HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
   }
	@GetMapping("/getCategoryById/{cId}")
	public ResponseEntity<Message<CategoryDto>>GetCategoryById(@PathVariable int cId) {
		log.info("In usercontroller login() with request:{}",cId);
		Message<CategoryDto> message=categoryservice.GetCategoryById(cId);
		HttpStatus httpStatus=HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@GetMapping("/GetAllCategories")
	public ResponseEntity<List<Message<CategoryDto>>>GetAllCategories() {
		List<Message<CategoryDto>> message=categoryservice.GetAllCategories();
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
}
		