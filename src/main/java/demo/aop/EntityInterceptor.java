package demo.aop;

import java.io.Serializable;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Component;

import demo.ApplicationContextProvider;
import demo.model.BaseEntity;

@Component
public class EntityInterceptor extends EmptyInterceptor {

	@Autowired
	ElasticsearchTemplate template;

	EntityManager manager;
	
	
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {

		
		if(manager == null)
			manager = ApplicationContextProvider.getApplicationContext().getBean(EntityManager.class);
		
		
		
		BaseEntity baseEntity = (BaseEntity) entity;
		
		
		Document d = entity.getClass().getAnnotation(Document.class);
		IndexQuery query = new IndexQuery();
		query.setIndexName(d.indexName());
		query.setId(baseEntity.getId().toString());
		query.setObject(baseEntity.convert(entity));
		query.setType(d.type());
		template.index(query);
		return false;
	}

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {

		Document d = entity.getClass().getAnnotation(Document.class);
		String s = template.delete(d.indexName(), d.type(),
				((BaseEntity) entity).getId().toString());
	}

	
}
