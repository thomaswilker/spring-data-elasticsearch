package demo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Category;

public interface CategoryJpaRespository extends JpaRepository<Category, Long>{

}
