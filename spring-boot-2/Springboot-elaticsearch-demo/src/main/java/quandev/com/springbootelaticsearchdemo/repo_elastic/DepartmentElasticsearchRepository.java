package quandev.com.springbootelaticsearchdemo.repo_elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import quandev.com.springbootelaticsearchdemo.entity.Department;

@Repository
public interface DepartmentElasticsearchRepository extends ElasticsearchRepository<Department, Long> {

}
