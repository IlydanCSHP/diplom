package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity(name = "schedules")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"group_id", "lesson_id"})
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Group group;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Lesson> lessons;

    @Column(name = "schedule_date")
    private Date date;

    public Schedule(Group group, List<Lesson> lessons, Date date) {
        this.group = group;
        this.lessons = lessons;
        this.date = date;
    }
}
