package demo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Book;

public interface BookJpaRespository extends JpaRepository<Book, Long>{

}
