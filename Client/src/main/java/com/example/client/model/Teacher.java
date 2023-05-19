package com.example.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Teacher {
    private Long id;
    private String name;
    private String secondName;
    private String lastName;
    private String commission;

    public Teacher(String name, String secondName, String lastName, String commission) {
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
        this.commission = commission;
    }
}
