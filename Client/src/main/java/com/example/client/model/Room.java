package com.example.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Room {
    private Long id;
    private Integer number;

    public Room(Integer number) {
        this.number = number;
    }
}
