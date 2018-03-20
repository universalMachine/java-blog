package com.wang.blog.domain;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue
    private Integer authorId;


    @Column(length = 50,nullable = false)
    private String authority;
}
