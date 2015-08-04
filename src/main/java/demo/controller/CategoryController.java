package demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.controller.base.CrudRestController;
import demo.model.Category;

@RestController
@RequestMapping("/category")
public class CategoryController extends CrudRestController<Category> {

	
	 
}
