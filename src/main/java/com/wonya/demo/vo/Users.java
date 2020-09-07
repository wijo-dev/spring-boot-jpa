package com.wonya.demo.vo;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@DynamicInsert
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;
    private String id;
    private String pw;
    private String name;
    private Date regDate;
    private Date lastDate;
}
