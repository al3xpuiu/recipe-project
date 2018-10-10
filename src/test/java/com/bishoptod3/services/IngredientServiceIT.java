package com.bishoptod3.services;

import com.bishoptod3.commands.IngredientCommand;
import com.bishoptod3.converters.IngredientToIngredientCommand;
import com.bishoptod3.domain.Ingredient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by Loky on 09/10/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IngredientServiceIT {

    private static final String DESCRIPTION = "A new description";

    private static final Long RECIPE_ID = 1L;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    @Test
    @Transactional
    public void testChangeOfDescription() {

        //given
        Set<Ingredient> ingredients = ingredientService.getIngredientsByRecipeId( RECIPE_ID );
        Ingredient ingredient = ingredients.stream().findFirst().orElse( null );
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert( ingredient );
        Assert.assertNotNull( ingredient );
        Assert.assertNotNull( ingredientCommand );

        //when
        ingredientCommand.setDescription( DESCRIPTION );
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredient(ingredientCommand);

        //then
        Assert.assertNotNull( savedIngredientCommand );
        Assert.assertEquals( DESCRIPTION, savedIngredientCommand.getDescription() );

    }
}
