package com.bishoptod3.commands;

import com.bishoptod3.domain.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Loky on 13/09/2018.
 */
@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

    private Long id;
    private String description;
    private UnitOfMeasure uom;
}