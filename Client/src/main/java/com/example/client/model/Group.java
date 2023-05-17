package com.example.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Group {
    private Long id;
    private String title;
    private String speciality;

    public Group(String title, String speciality) {
        this.title = title;
        this.speciality = speciality;
    }
}
