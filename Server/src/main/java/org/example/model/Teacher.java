package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "teacher_name")
    private String name;
    @Column(name = "teacher_second_name")
    private String secondName;
    @Column(name = "teacher_last_name")
    private String lastName;
    @Column(name = "teacher_commission")
    private String commission;


    public Teacher(String name, String secondName, String lastName, String commission) {
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
        this.commission = commission;
    }
}

