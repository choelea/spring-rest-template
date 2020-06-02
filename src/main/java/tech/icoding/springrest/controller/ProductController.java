package tech.icoding.springrest.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import tech.icoding.springrest.data.MsgData;
import tech.icoding.springrest.entities.Product;
import tech.icoding.springrest.entities.Product_;
import tech.icoding.springrest.form.RatingForm;
import tech.icoding.springrest.repo.ProductRepository;
import tech.icoding.springrest.repo.ProductSpecifications;

@RestController
@RequestMapping("/products")
public class ProductController {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/{id}")
	public Product get(@PathVariable("id") Long id) {
		Product product = productRepository.getOne(id);
		product.setRanking(product.getRanking() + 1);
		productRepository.save(product);
		return product;
	}

	@GetMapping
	public Page<Product> get(Pageable pageable, Long category, String name) {
		Specification<Product> specifications = ProductSpecifications.conjunction();
		if (category != null) {
			specifications = specifications.and(ProductSpecifications.withCategory(category));
		}
		if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(name.trim())) {
			if (name.contains("%")) {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "非法字符");
			} else {
				specifications = specifications.and(ProductSpecifications.nameContains(name));
			}
		}
		return productRepository.findAll(specifications, pageable);
	}

	@DeleteMapping("/{id}")
	public MsgData delete(@PathVariable("id") Long id) {
		try {
			productRepository.deleteById(id);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return MsgData.success;
	}

	@PostMapping
	public Product create(@Validated @RequestBody Product product) {
		//checkCategory(product.getCategory());
		return productRepository.save(product);
	}

	@PostMapping("/rating")
	public Product rating(@Validated @RequestBody RatingForm ratingForm) {
		Product product = productRepository.getOne(ratingForm.getProductId());
		Long ratingCount = product.getRatingCount();
		Float rating = (product.getRating() * ratingCount + ratingForm.getRating().floatValue()) / (ratingCount + 1);
		product.setRating(rating);
		product.setRatingCount(ratingCount + 1);
		product = productRepository.save(product);
		return product;
	}

	@PutMapping
	public Product update(@Validated @RequestBody Product product) {
		Optional<Product> option = productRepository.findById(product.getId());
		Product u = option.get();
		
		BeanUtils.copyProperties(product, u, Product_.ID);
		productRepository.save(u);
		return u;
	}

	
}
