package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

@Configuration
@SpringBootApplication
@EnableAutoConfiguration
@EnableSpringConfigured
public class SpringTestApplication {

	
	public static void main(String[] args) {
        SpringApplication.run(SpringTestApplication.class, args);
    }
}
