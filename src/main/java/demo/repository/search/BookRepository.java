package demo.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import demo.model.Book;

public interface BookRepository extends ElasticsearchRepository<Book, Long> {
	
	public Iterable<Book> findByCategoryId(Long id);
}
