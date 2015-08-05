package demo.model;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.apache.log4j.Logger;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;



public abstract class BaseEntity<T> {
	
	Logger log = Logger.getLogger(this.getClass());
	
	@Id
	@Field(type=FieldType.Long,store=true)
	@Column(name="id", unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Long id = null;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	protected void index(Object entity, Long id, ElasticsearchTemplate template) {
		
		Document d = entity.getClass().getAnnotation(Document.class);
		IndexQuery query = new IndexQuery();
		query.setIndexName(d.indexName());
		query.setId(id.toString());
		query.setObject(entity);
		query.setType(d.type());
		template.index(query);
	}
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("postConstruct");
	}
	
}
