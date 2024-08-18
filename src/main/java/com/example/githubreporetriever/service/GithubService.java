package com.example.githubreporetriever.service;

import com.example.githubreporetriever.model.Branch;
import com.example.githubreporetriever.model.Repository;
import com.example.githubreporetriever.model.dto.BranchDto;
import com.example.githubreporetriever.model.dto.RepositoryDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import java.util.concurrent.CompletableFuture;

@Service
public class GithubService {

    private final RestTemplate restTemplate;
    private final String githubToken;

    public GithubService(RestTemplateBuilder restTemplateBuilder, @Value("${github.api.token}") String githubToken) {
        this.restTemplate = restTemplateBuilder.build();
        this.githubToken = githubToken;
    }

    public List<RepositoryDto> getRepositories(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Repository[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Repository[].class);

        return Arrays.stream(Objects.requireNonNull(response.getBody()))
                .filter(repo -> !repo.isFork())
                .map(repo -> new RepositoryDto(repo.getName(), repo.getOwner().getLogin(), getBranchesAsync(repo).join()))
                .collect(Collectors.toList());
    }

    private CompletableFuture<List<BranchDto>> getBranchesAsync(Repository repo) {
        return CompletableFuture.supplyAsync(() -> {
            String url = "https://api.github.com/repos/" + repo.getOwner().getLogin() + "/" + repo.getName() + "/branches";
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + githubToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Branch[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Branch[].class);

            return Arrays.stream(Objects.requireNonNull(response.getBody()))
                    .map(branch -> new BranchDto(branch.getName(), branch.getCommit().getSha()))
                    .collect(Collectors.toList());
        });
    }
}