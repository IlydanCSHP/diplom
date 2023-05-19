package com.example.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Subject {
    private Long id;

    private String title;

    public Subject(String title) {
        this.title = title;
    }
}
