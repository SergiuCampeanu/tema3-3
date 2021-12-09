package com.company.repository;

public interface IFileRepository<E> extends CrudRepository<E>{
    String getFileName();

}
