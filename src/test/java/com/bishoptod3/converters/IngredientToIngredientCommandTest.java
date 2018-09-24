package com.bishoptod3.converters;

import com.bishoptod3.commands.IngredientCommand;
import com.bishoptod3.domain.Ingredient;
import com.bishoptod3.domain.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by Loky on 13/09/2018.
 */
public class IngredientToIngredientCommandTest {

    private IngredientToIngredientCommand converter;

    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter;

    private static final Long ID_INGREDIENT = 1L;
    private static final String DESCRIPTION_INGREDIENT = "Some description";
    private static final BigDecimal AMOUNT_INGREDIENT = BigDecimal.ONE;
    private static final Long ID_UNIT_OF_MEASURE = 2L;
    private static final String DESCRIPTION_UNIT_OF_MEASURE = "Another description";

    @Before
    public void setUp() {

        unitOfMeasureConverter = new UnitOfMeasureToUnitOfMeasureCommand();
        converter = new IngredientToIngredientCommand(unitOfMeasureConverter);
    }

    @Test
    public void testNullObject() {
        Assert.assertNull(converter.convert(null));
    }

    @Test
    public void testNotNullObject() {
        Assert.assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    public void testConverter() {

        //given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID_UNIT_OF_MEASURE);
        unitOfMeasure.setDescription(DESCRIPTION_UNIT_OF_MEASURE);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_INGREDIENT);
        ingredient.setDescription(DESCRIPTION_INGREDIENT);
        ingredient.setAmount(AMOUNT_INGREDIENT);
        ingredient.setUom(unitOfMeasure);

        //when
        IngredientCommand ingredientCommand = converter.convert(ingredient);

        //then
        Assert.assertNotNull(ingredientCommand);
        Assert.assertEquals(ID_INGREDIENT, ingredient.getId());
        Assert.assertEquals(DESCRIPTION_INGREDIENT, ingredient.getDescription());
        Assert.assertEquals(AMOUNT_INGREDIENT, ingredient.getAmount());
        Assert.assertEquals(ID_UNIT_OF_MEASURE, ingredient.getUom().getId());
        Assert.assertEquals(DESCRIPTION_UNIT_OF_MEASURE, ingredient.getUom().getDescription());


    }

}