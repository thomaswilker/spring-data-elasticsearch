package demo;

import java.lang.reflect.Field;

import javax.persistence.Entity;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import demo.annotation.Update;
import demo.model.Category;

public class Scanner {

	
	public static void main(String args[]) throws ClassNotFoundException {
		
		
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
		for (BeanDefinition bd : scanner.findCandidateComponents("demo/model")) {
			Class<?> clazz = Class.forName(bd.getBeanClassName());
			for(Field f : clazz.getFields()) {
				if(f.isAnnotationPresent(Update.class) && f.getType().equals(Category.class)) {
					System.out.println(f.getName());
				} 
			}
			
		}
	}
	
}
