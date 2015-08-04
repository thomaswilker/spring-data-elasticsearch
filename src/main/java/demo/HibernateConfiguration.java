package demo;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.hibernate.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration extends HibernateJpaAutoConfiguration{

	@Autowired
	Interceptor entityInterceptor;
	
	
	@Override
	protected void customizeVendorProperties(Map<String, Object> vendorProperties) {
		//vendorProperties.put("hibernate.ejb.interceptor",entityInterceptor);
	}

	 
}
