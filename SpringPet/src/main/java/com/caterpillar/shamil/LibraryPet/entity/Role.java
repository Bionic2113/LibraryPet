package com.caterpillar.shamil.LibraryPet.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "roles")
public class Role {
    @Id
    private int id;

    @OneToMany
    @JoinColumn(name = "role_")
    private List<Users> users;
}
