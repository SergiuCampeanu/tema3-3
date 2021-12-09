package com.company.exceptions.RepositoryExceptions;

public class RepoException extends RuntimeException {

    public RepoException(String errorMessage) {
        super(errorMessage);
    }
}
