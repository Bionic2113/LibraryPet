package com.caterpillar.shamil.SpringSecurity.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "people")
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "active")
    private boolean active = true;

    public People(String lastname, String firstname, String patronymic) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.patronymic = patronymic;
    }
}
