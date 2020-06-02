package tech.icoding.springrest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tech.icoding.springrest.entities.Category;
import tech.icoding.springrest.repo.CategoryRepository;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/{id}")
    public Category get(@PathVariable("id") Long id) {
    	return categoryRepository.findById(id).get();
    }

    @GetMapping
    public Page<Category> get(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
    	categoryRepository.deleteById(id);
    }
    @PostMapping
    public Category create(Category category) {
    	return categoryRepository.save(category);
    }
    
    @PutMapping
    public Category update(Category category) {
    	Optional<Category> option = categoryRepository.findById(category.getId());
    	if(option.isPresent()) {
    		Category u = option.get();
    		u.setName(category.getName());
    		return u;
    	}
    	
    	return null;
    }
}
