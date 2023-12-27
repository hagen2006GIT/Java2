package ru.course2.spring1;

import lombok.Getter;
import lombok.Setter;

// Data Transfer Object (DTO)
public class Users {
    @Setter @Getter private int id;
    @Setter @Getter private String username;
    @Setter @Getter private String fio;
    public Users(int id,String name,String fio) {
        this.id=id;
        this.username=name;
        this.fio=fio;
    }
}
