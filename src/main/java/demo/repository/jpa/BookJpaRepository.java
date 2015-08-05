package demo.repository.jpa;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import demo.model.Book;

@Repository
public class BookJpaRepository extends SimpleJpaRepository<Book, Long> {

	@Autowired
	public BookJpaRepository(Class<Book> domainClass, EntityManager em) {
		super(domainClass, em);
	}
}
