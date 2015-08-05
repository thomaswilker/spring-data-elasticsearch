package demo.controller;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TestAdvice {

	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	ElasticsearchTemplate template;
	
	@Around(value="execution(* demo.repository.jpa.BookJpaRepository.save(..))")
	public Object repository(ProceedingJoinPoint pjp) throws Throwable {
		
		for(Object o : pjp.getArgs()) {
			System.out.println(o);
		}
		
		Object object = pjp.proceed();
		return object;
	}
	
	@Around(value="execution(* demo.controller.CrudRestController.update(..))")
	public Object controller(ProceedingJoinPoint pjp) throws Throwable {
		
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
		
		
		Object object = pjp.proceed();
		return object;
	}
}
