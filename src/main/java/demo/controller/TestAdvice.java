package demo.controller;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.lang.reflect.Field;

import javax.persistence.Entity;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

import demo.annotation.Update;
import demo.model.BaseEntity;

@Aspect
@Component
public class TestAdvice {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	ElasticsearchTemplate template;
	
	@Around(value="execution(* demo.controller.CrudRestController.update(..))")
	public Object controller(ProceedingJoinPoint pjp) throws Throwable {
		
		BaseEntity e = (BaseEntity) pjp.getArgs()[1];
		for(Object o : pjp.getArgs())
			logger.info(o.toString());
		
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
		for (BeanDefinition bd : scanner.findCandidateComponents("demo/model")) {
			Class<?> clazz = Class.forName(bd.getBeanClassName());
			for(Field f : clazz.getFields()) {
				if(f.isAnnotationPresent(Update.class) && f.getType().equals(e.getClass())) {
					
					SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
					
				} 
			}
			
		}
		
		
		Object object = pjp.proceed();
		return object;
	}
}
