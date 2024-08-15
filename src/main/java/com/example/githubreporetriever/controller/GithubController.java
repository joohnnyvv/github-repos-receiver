package com.example.githubreporetriever.controller;

import com.example.githubreporetriever.model.ApiErrorResponse;
import com.example.githubreporetriever.model.dto.RepositoryDto;
import com.example.githubreporetriever.service.GithubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/repos/{username}")
    public ResponseEntity<?> getRepos(@PathVariable String username) {
        try {
            List<RepositoryDto> repositories = githubService.getRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found"));
        }
    }
}
