package demo.controller.base;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import demo.model.BaseEntity;


public abstract class CrudRestController<T extends BaseEntity>  {

	@Autowired
	protected ElasticsearchRepository<T, Long> searchRepository; 
	
	@Autowired
	protected JpaRepository<T, Long> jpaRepository; 
	
	
	@Autowired
	protected ElasticsearchTemplate template;
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping(method=RequestMethod.GET)
	public Page<T> list(Pageable pageable) {
		return searchRepository.findAll(pageable);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<T> save(T entity) {
		//searchRepository.save(entity)
		return ResponseEntity.ok(jpaRepository.save(entity));
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	public void deleteAll() {
		jpaRepository.deleteAll();
		searchRepository.deleteAll();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<T> update(@PathVariable("id") Long id, @RequestBody T entity) {
		
		logger.info("update");
		entity.setId(id);
		jpaRepository.save(entity);
		return new ResponseEntity<T>(searchRepository.save(entity), HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<T> delete(@PathVariable("id") Long id) {
		jpaRepository.delete(id);
		searchRepository.delete(id);
		return new ResponseEntity<T>(HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public Iterable<T> listAll() {
		Iterable<T> list = searchRepository.findAll();
		return list; 
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String get(@PathVariable("id") T entity,
					  @RequestParam(value="pretty", required=false, defaultValue="true")  Boolean pretty,
					  @RequestParam(value="pick", required=false, defaultValue="")  Set<String> pick,
					  @RequestParam(value="omit", required=false, defaultValue="")  Set<String> omit) throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filter = new SimpleFilterProvider().addFilter("test", SimpleBeanPropertyFilter.filterOutAllExcept(pick));
		ObjectWriter writer = mapper.writer(filter);
		
		if(pretty)
			writer = writer.withDefaultPrettyPrinter();
		
		String json = writer.writeValueAsString(entity);
		
		return json;
	}
	
	
	
	
}
