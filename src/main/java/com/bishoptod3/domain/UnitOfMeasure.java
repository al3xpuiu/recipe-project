package com.bishoptod3.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Loky on 22/08/2018.
 */
@Data
@EqualsAndHashCode(exclude = "ingredient")
@ToString(exclude = "ingredient")
@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToMany(mappedBy = "uom")
    private Set<Ingredient> ingredient = new HashSet<>(  );
}
