package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"schedule"}, allowSetters = true)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(mappedBy = "group", cascade = CascadeType.ALL)
    private Schedule schedule;

    @Column(name = "group_title")
    private String title;

    @Column(name = "group_speciality")
    private String speciality;

    public Group(String title, String speciality) {
        this.title = title;
        this.speciality = speciality;
    }
}
