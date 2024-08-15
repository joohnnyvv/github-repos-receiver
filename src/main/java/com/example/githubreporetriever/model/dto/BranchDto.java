package com.example.githubreporetriever.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchDto {

    private String name;
    private String lastCommitSha;

    public BranchDto(String name, String lastCommitSha) {
        this.name = name;
        this.lastCommitSha = lastCommitSha;
    }
}
