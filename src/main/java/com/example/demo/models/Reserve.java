package com.example.demo.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reserve")
public class Reserve implements Serializable {

    public Reserve() {

    }
    @Id
    @SequenceGenerator(
            name = "reserve_id_sequence",
            sequenceName = "reserve_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reserve_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Catering catering;
}
