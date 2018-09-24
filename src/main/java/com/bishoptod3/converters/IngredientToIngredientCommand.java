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
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private UnitOfMeasureToUnitOfMeasureCommand uom;

    @Autowired
    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uom) {
        this.uom = uom;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(@Nullable Ingredient source) {

        if (source == null)
            return null;

        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(source.getId());
        ingredientCommand.setDescription(source.getDescription());
        ingredientCommand.setAmount(source.getAmount());
        ingredientCommand.setUom(uom.convert(source.getUom()));

        return ingredientCommand;
    }
}
