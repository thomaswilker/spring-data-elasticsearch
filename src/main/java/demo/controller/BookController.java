package demo.controller;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.model.Book;
import demo.model.Category;
import demo.repository.jpa.CategoryJpaRespository;
import demo.repository.search.CategoryRepository;

@RestController
@RequestMapping("/book")
public class BookController extends CrudRestController<Book> {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	CategoryJpaRespository categoryJpaRepository;
	
	@RequestMapping(value="/filter", method=RequestMethod.GET)
	public Iterable<Book> filter(@RequestParam Map<String,String> allRequestParams, 
								 @RequestParam(value="query", required=false) String query,
								 @RequestParam(value="category", required=false) Long category,
								 Pageable pageable) {
		
		BoolQueryBuilder queryBuilder = boolQuery().must(matchAllQuery());
		
		if(query != null)
			queryBuilder.must(matchPhraseQuery("name", query));
		
		if(category != null)
			queryBuilder.must(matchQuery("category.id", category));
		
		return searchRepository.search(queryBuilder, pageable);
	}
	
	
	@RequestMapping(value="/init", method=RequestMethod.GET)
	public Iterable<Book> init() {
		
		jpaRepository.deleteAll();
		searchRepository.deleteAll();
		categoryRepository.deleteAll();
		categoryJpaRepository.deleteAll();
		
		Category c1 = new Category(1l, "Roman");
		Category c2 = new Category(2l, "Krimi");
		categoryJpaRepository.save(c1);
		categoryJpaRepository.save(c2);
		
		List<Book> books = LongStream.range(20, 40).boxed().map(i -> new Book(i, "Book " + i, (int) Math.ceil(100 * Math.random()),  (i % 2 == 0) ? c1 : c2)).collect(Collectors.toList());
		jpaRepository.save(books);
		
		return searchRepository.findAll();
	}
	
	
}
