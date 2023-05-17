package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"lessons"}, allowSetters = true)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private List<Lesson> lessons;
    @Column(name = "room_number")
    private Integer number;

    public Room(Integer number) {
        this.number = number;
    }
}
