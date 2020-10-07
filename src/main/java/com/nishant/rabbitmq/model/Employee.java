package com.nishant.rabbitmq.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Employee implements Serializable {
    private static final long serialVersionUID = 4539556712678278705L;

    private String id;

    private String name;

    private Integer salary;
}
