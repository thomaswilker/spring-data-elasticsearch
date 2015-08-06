package demo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Book;

public interface BookJpaRepository extends JpaRepository<Book, Long>{

}
