package com.bishoptod3.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Loky on 22/08/2018.
 */
@Data
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeNotes;

}
