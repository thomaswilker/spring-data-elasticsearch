package demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.apache.activemq.filter.function.makeListFunction;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name="category")
@Document(indexName = "books", type = "category" , shards = 1, replicas = 1, indexStoreType = "fs", refreshInterval = "1")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Category extends BaseEntity {

	@Override
	@Id
	@GeneratedValue(generator="generator")
	@GenericGenerator(name="generator",strategy="increment")
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	
	@Field(type=FieldType.String,store=true)
	public String name = null;
	
	private List<Book> books = new ArrayList<Book>();
	
	public Category() {}
	
	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy="category")
	public List<Book> getBooks() {
		return this.books;
	}
	
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
}
