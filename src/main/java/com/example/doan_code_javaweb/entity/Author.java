package com.example.doan_code_javaweb.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Author {
    @Id
    @Column(name = "author_id")
    private String authorId;
    private String name;
}
