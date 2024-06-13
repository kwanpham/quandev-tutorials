package quandev.com.springbootelaticsearchdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "quandev.com.springbootelaticsearchdemo.repo")
@EnableElasticsearchRepositories(basePackages = "quandev.com.springbootelaticsearchdemo.repo_elastic")
@SpringBootApplication
public class SpringbootElaticsearchDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootElaticsearchDemoApplication.class, args);
	}

}
