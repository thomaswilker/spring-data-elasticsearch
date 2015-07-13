package demo.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFilter;



public abstract class BaseEntity {
	
	
	
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
}