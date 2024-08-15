package com.example.githubreporetriever.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {

    private String name;
    private Owner owner;
    private boolean fork;

    @JsonProperty("branches_url")
    private String branchesUrl;

}
