package demo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Book;
import demo.model.Category;

public interface CategoryJpaRepository extends JpaRepository<Category, Long>{

}
