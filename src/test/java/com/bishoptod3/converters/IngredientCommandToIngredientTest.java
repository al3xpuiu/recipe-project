package com.bishoptod3.converters;

import com.bishoptod3.commands.IngredientCommand;
import com.bishoptod3.commands.UnitOfMeasureCommand;
import com.bishoptod3.domain.Ingredient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by Loky on 13/09/2018.
 */
public class IngredientCommandToIngredientTest {

    private IngredientCommandToIngredient converter;

    private UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandConverter;

    private static final Long ID_INGREDIENT_COMMAAND = 1L;
    private static final String DESCRIPTION_INGREDIENT_COMMAND = "Some description";
    private static final BigDecimal AMOUNT_INGREDIENT_COMMAND = BigDecimal.ONE;
    private static final Long ID_UNIT_OF_MEASURE_COMMAND = 2L;
    private static final String DESCRIPTION_UNIT_OF_MEASURE_COMMAND = "Another description";

    @Before
    public void setUp() {

        unitOfMeasureCommandConverter = new UnitOfMeasureCommandToUnitOfMeasure();
        converter = new IngredientCommandToIngredient(unitOfMeasureCommandConverter);

    }

    @Test
    public void testNullObject() {
        Assert.assertNull(converter.convert(null));
    }

    @Test
    public void testNonNullObject() {
        Assert.assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    public void testConverter() {

        //given
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID_UNIT_OF_MEASURE_COMMAND);
        unitOfMeasureCommand.setDescription(DESCRIPTION_UNIT_OF_MEASURE_COMMAND);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_INGREDIENT_COMMAAND);
        ingredientCommand.setDescription(DESCRIPTION_INGREDIENT_COMMAND);
        ingredientCommand.setAmount(AMOUNT_INGREDIENT_COMMAND);
        ingredientCommand.setUom(unitOfMeasureCommand);

        //when
        Ingredient ingredient = converter.convert(ingredientCommand);

        //then

        Assert.assertNotNull(ingredient);
        Assert.assertEquals(ID_INGREDIENT_COMMAAND, ingredient.getId());
        Assert.assertEquals(DESCRIPTION_INGREDIENT_COMMAND, ingredient.getDescription());
        Assert.assertEquals(AMOUNT_INGREDIENT_COMMAND, ingredient.getAmount());
        Assert.assertEquals(ID_UNIT_OF_MEASURE_COMMAND, ingredient.getUom().getId());
        Assert.assertEquals(DESCRIPTION_UNIT_OF_MEASURE_COMMAND, ingredient.getUom().getDescription());
    }

}