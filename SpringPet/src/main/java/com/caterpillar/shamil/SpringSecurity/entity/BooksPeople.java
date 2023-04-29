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
@Table(name = "bookspeople")
public class BooksPeople {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id_books")
    private Books books;

    @ManyToOne
    @JoinColumn(name = "id_people")
    private People peoples;

    @Column(name = "active")
    private boolean active = true;

    public BooksPeople(Books books, People peoples) {
        this.books = books;
        this.peoples = peoples;
    }
}
