package com.example.githubreporetriever.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepositoryDto {

    private String name;
    private String ownerLogin;
    private List<BranchDto> branches;

    public RepositoryDto(String name, String ownerLogin, List<BranchDto> branches) {
        this.name = name;
        this.ownerLogin = ownerLogin;
        this.branches = branches;
    }
}
