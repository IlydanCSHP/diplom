package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "subjects")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"lessons"}, allowSetters = true)
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<Lesson> lessons;
    private String title;

    public Subject(String title) {
        this.title = title;
    }
}
