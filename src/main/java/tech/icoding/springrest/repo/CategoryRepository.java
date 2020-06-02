package tech.icoding.springrest.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tech.icoding.springrest.entities.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>{
	
}
