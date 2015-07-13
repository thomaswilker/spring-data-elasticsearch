package demo.aop;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Component;

import demo.model.BaseEntity;

@Component
public class EntityInterceptor extends EmptyInterceptor {

	@Autowired
	ElasticsearchTemplate template;
	
	@Override
	public boolean onSave(
			Object entity, 
			Serializable id, 
			Object[] state, 
			String[] propertyNames, 
			Type[] types) {
		
		BaseEntity baseEntity = (BaseEntity) entity;
		Document d = entity.getClass().getAnnotation(Document.class);
		IndexQuery query = new IndexQuery();
		query.setIndexName(d.indexName());
		query.setId(baseEntity.getId().toString()); 
		query.setObject(entity);
		query.setType(d.type());
		template.index(query);
		System.out.println("save");
		return false;
	}
	
}
