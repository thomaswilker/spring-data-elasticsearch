package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@EnableSpringConfigured
@ComponentScan
public class SpringTestApplication extends SpringApplication {

	public static ConfigurableApplicationContext context;
	
	public static void main(String[] args) {
        
		context = (ConfigurableApplicationContext) SpringApplication.run(SpringTestApplication.class, args);
    }
}
