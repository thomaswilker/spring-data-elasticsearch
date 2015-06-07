package demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ComponentScan(basePackages={"demo.controller"})
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages="demo.repository.jpa")
public class WebConfig extends WebMvcConfigurerAdapter {

	
}
