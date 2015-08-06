package demo.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestService {

	@Value("${spring.datasource.url}") 
	private String url;
	
	public String getTest() {
		return "test";
	}
	
	public TestService() {
	}
	
	
	@PostConstruct
	public void postConstruct() {
		System.out.println(String.format("url=%s", url));
	}
	
}
