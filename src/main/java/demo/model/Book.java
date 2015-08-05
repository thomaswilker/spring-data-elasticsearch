package demo.model;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.NestedField;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import demo.SpringTestApplication;
import demo.annotation.Update;


@Entity(name="book")
@Document(indexName = "books", type = "book" , shards = 1, replicas = 1, indexStoreType = "fs", refreshInterval = "1")
public class Book extends BaseEntity<Book> {

	@Field(type=FieldType.String,store=true)
	public String name = null;
	
	@Field(type=FieldType.Integer,store=true)
	public int pages = 0;
	
	@Update
	@NestedField(type=FieldType.Object, dotSuffix = "category")
	public Category category = null;
	
	public Book() {}
	
	public Book(Long id, String name, int pages, Category cat) {
		this.id = id;
		this.name = name;
		this.pages = pages;
		this.category = cat;
	}
	
	@Override
	@Id
	@GeneratedValue(generator="generator")
	@GenericGenerator(name="generator",strategy="increment")
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	
	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="pages")
	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "categoryId", nullable = true)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@PostPersist
	public void postPersist() {
		
		EntityManager entityManager = SpringTestApplication.context.getBean(EntityManager.class);	
		ElasticsearchTemplate template = SpringTestApplication.context.getBean(ElasticsearchTemplate.class);	
		ElasticsearchRepository<Book, Long> respository;	

		Book b = entityManager.find(this.getClass(), this.getId());
		
		
	}
	
	@Override
	public Object convert(Book entity) {
		
		log.info(entity.getCategory().getBooks().size());
		return entity;
	}
}
