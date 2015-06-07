package demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Entity(name="category")
@Document(indexName = "books", type = "category" , shards = 1, replicas = 1, indexStoreType = "fs", refreshInterval = "1")
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
	
	
}
