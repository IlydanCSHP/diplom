package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "groups")
@Getter
@Setter
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "group_title")
    private String title;

    @Column(name = "group_speciality")
    private String speciality;

    public Group(String title, String speciality) {
        this.title = title;
        this.speciality = speciality;
    }
}
