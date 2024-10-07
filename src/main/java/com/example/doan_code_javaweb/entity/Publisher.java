package com.example.doan_code_javaweb.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Publisher {
    @Id
    @Column(name="publisher_id")
    private String publisherId;
    private String name;
}
