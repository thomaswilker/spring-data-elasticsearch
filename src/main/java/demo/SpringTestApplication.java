package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@EnableSpringConfigured
@ImportResource({"classpath*:applicationContext.xml"})
public class SpringTestApplication extends SpringApplication {

	public static ApplicationContext context;
	
	public static void main(String[] args) {
        
		context = (ApplicationContext) SpringApplication.run(SpringTestApplication.class, args);
    }
}
