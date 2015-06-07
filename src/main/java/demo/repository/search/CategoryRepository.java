package demo.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import demo.model.Category;

public interface CategoryRepository extends ElasticsearchRepository<Category, Long>{

}
