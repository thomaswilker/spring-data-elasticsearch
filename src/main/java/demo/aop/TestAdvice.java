package demo.aop;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import demo.SpringTestApplication;
import demo.annotation.Update;
import demo.model.BaseEntity;

@Aspect
@Component
public class TestAdvice {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	ElasticsearchTemplate template;
	
	@SuppressWarnings({"unchecked"})
	protected <T> T getTargetObject(Object proxy, Class<T> targetClass) throws Exception {
	  if (AopUtils.isJdkDynamicProxy(proxy)) {
	    return (T) ((Advised) proxy).getTargetSource().getTarget();
	  } else {
	    return (T) proxy; // expected to be cglib proxy then, which is simply a specialized class
	  }
	}
	
	@SuppressWarnings({"unchecked"})
	private ElasticsearchRepository<BaseEntity, Long> getRepository(BaseEntity entity) throws Exception {
		Map<String, ElasticsearchRepository> repositories = SpringTestApplication.context.getBeansOfType(ElasticsearchRepository.class);
		String cananicalName = entity.getClass().getName();
		String className = cananicalName.substring(cananicalName.lastIndexOf('.') + 1).toLowerCase();
		String repositoryName = String.format("%sRepository", className);
		repositories.keySet().stream().forEach(r -> System.out.println(r));
		ElasticsearchRepository<?, Long> repository = repositories.get(repositoryName);
			
		return getTargetObject(repository, ElasticsearchRepository.class);
		
	}
	
	@Around(value="execution(* demo.repository.jpa.*.save(..)) && args(list)")
	public Object repositorySaveList(ProceedingJoinPoint pjp, List<BaseEntity> list) throws Throwable {
		
		Object object = pjp.proceed();
		if(list.size() > 0) {
			getRepository(list.get(0)).save(list);
		}
		return object;
	}
	
	@Around(value="execution(* demo.repository.jpa.*.save(..)) && args(entity)")
	public Object repositorySave(ProceedingJoinPoint pjp, BaseEntity entity) throws Throwable {
		
		Object object = pjp.proceed();
		getRepository(entity).save(entity);
		return object;
	}
	
//	@Around(value="execution(* demo.repository.jpa.*.deleteAll(..))")
//	public Object repositoryDeleteAll(ProceedingJoinPoint pjp) throws Throwable {
//		
//		Object object = pjp.proceed();
//		getRepository(entity).deleteAll();
//		return object;
//	}

	@Around(value="execution(* demo.repository.jpa.*.delete(..))  && args(entity)")
	public Object repositoryDelete(ProceedingJoinPoint pjp, BaseEntity entity) throws Throwable {
		
		Object object = pjp.proceed();
		getRepository(entity).delete(entity);
		return object;
	}
	
	
//	@Around(value="execution(* demo.controller.base.CrudRestController.update(..))")
//	public Object controller(ProceedingJoinPoint pjp) throws Throwable {
//		
//		BaseEntity e = (BaseEntity) pjp.getArgs()[1];
//		for(Object o : pjp.getArgs())
//			logger.info(o.toString());
//		
//		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
//		scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
//		for (BeanDefinition bd : scanner.findCandidateComponents("demo/model")) {
//			Class<?> clazz = Class.forName(bd.getBeanClassName());
//			for(Field f : clazz.getFields()) {
//				if(f.isAnnotationPresent(Update.class) && f.getType().equals(e.getClass())) {
//					
//					SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
//					
//				} 
//			}
//			
//		}
//		
//		
//		Object object = pjp.proceed();
//		return object;
//	}
}
