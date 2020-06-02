package tech.icoding.springrest.repo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import tech.icoding.springrest.entities.Category;
import tech.icoding.springrest.entities.Category_;
import tech.icoding.springrest.entities.Product;
import tech.icoding.springrest.entities.Product_;

public interface ProductSpecifications {
	public static Specification<Product> conjunction(){
		return (root, cq, cb) -> cb.conjunction();
	}
	public static Specification<Product> withCategory(Long category) {
		return new Specification<Product>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4033929407472074008L;

			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Join<Product, Category> join = root.join(Product_.CATEGORIES);
				Predicate predicate = join.getOn();
				predicate = cb.and(cb.equal(join.get(Category_.ID), category));
				 
	            return predicate;
			}
		};
    }
	public static Specification<Product> nameContains(String name) {
        return (root, cq, cb) -> cb.like(root.get(Product_.name), "%" + name + "%");
    }
}
