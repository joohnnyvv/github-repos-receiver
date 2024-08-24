package com.example.githubreporetriever.controller;

import com.example.githubreporetriever.model.dto.BranchDto;
import com.example.githubreporetriever.model.dto.RepositoryDto;
import com.example.githubreporetriever.service.GithubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GithubController.class)
public class GithubControllerTest {

    @MockBean
    private GithubService githubService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetRepos_UserExists_ReturnsRepositories() throws Exception {
        String username = "existingUser";

        BranchDto branch1 = new BranchDto("main", "sha1");
        BranchDto branch2 = new BranchDto("develop", "sha2");

        RepositoryDto repo1 = new RepositoryDto("repo1", "existingUser", List.of(branch1));
        RepositoryDto repo2 = new RepositoryDto("repo2", "existingUser", List.of(branch2));

        when(githubService.getRepositories(username)).thenReturn(Arrays.asList(repo1, repo2));

        mockMvc.perform(get("/api/github/repos/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("repo1"))
                .andExpect(jsonPath("$[0].ownerLogin").value("existingUser"))
                .andExpect(jsonPath("$[0].branches[0].name").value("main"))
                .andExpect(jsonPath("$[1].name").value("repo2"))
                .andExpect(jsonPath("$[1].ownerLogin").value("existingUser"))
                .andExpect(jsonPath("$[1].branches[0].name").value("develop"));
    }

    @Test
    public void testGetRepos_UserNotFound_ReturnsNotFound() throws Exception {
        String username = "nonExistentUser";

        when(githubService.getRepositories(username)).thenThrow(
                HttpClientErrorException.create(
                        HttpStatus.NOT_FOUND,
                        "User not found",
                        HttpHeaders.EMPTY,
                        null,
                        null
                )
        );

        mockMvc.perform(get("/api/github/repos/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found"));
    }
}
