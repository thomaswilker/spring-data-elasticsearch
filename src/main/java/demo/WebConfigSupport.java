package demo;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import demo.aop.TestAdvice;

@Configuration
@EnableElasticsearchRepositories(basePackages="demo.repository.search")
@EnableSpringDataWebSupport
public class WebConfigSupport extends WebMvcConfigurationSupport {

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() {
		return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
	}
	
	@Bean
	  public DomainClassConverter<?> domainClassConverter() {
	    return new DomainClassConverter<FormattingConversionService>(mvcConversionService());
	}
	
	
}
