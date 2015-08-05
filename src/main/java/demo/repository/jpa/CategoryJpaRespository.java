package demo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.model.Category;

@Repository
public interface CategoryJpaRespository extends JpaRepository<Category, Long>{

}
