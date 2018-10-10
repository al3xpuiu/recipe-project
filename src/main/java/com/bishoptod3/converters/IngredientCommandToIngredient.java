package com.bishoptod3.converters;

import com.bishoptod3.commands.IngredientCommand;
import com.bishoptod3.domain.Ingredient;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by Loky on 13/09/2018.
 */
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private UnitOfMeasureCommandToUnitOfMeasure uom;

    @Autowired
    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uom) {
        this.uom = uom;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(@Nullable IngredientCommand source) {

        if (source == null)
            return null;

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setDescription(source.getDescription());
        ingredient.setAmount(source.getAmount());
        ingredient.setUom(uom.convert(source.getUom()));


        return ingredient;
    }
}
