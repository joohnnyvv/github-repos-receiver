package com.example.githubreporetriever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GithubRepoRetrieverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubRepoRetrieverApplication.class, args);
	}

}
