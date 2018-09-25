package com.bishoptod3.repositories;

import com.bishoptod3.domain.Ingredient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;

/**
 * Created by DD on 9/24/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IngredientRepositoryIT {

    private static final Long RECIPE_ID = 1L;
    private static final Long INGREDIENT_ID = 1L;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    public void findAllByRecipeIdTest() {

        //given

        //when
        Set<Ingredient> ingredients = ingredientRepository.findAllByRecipeId(RECIPE_ID);

        //then
        Assert.assertNotNull(ingredients);
        Assert.assertEquals(5, ingredients.size());

    }

    @Test
    public void findByRecipeIdAndIdTest() {

        //given

        //when
        Optional<Ingredient> ingredientOptional = ingredientRepository.findByRecipeIdAndId( RECIPE_ID, INGREDIENT_ID );
        Ingredient ingredient = ingredientOptional.orElse( null );

        //then
        Assert.assertNotNull( ingredient );
        Assert.assertEquals( RECIPE_ID, ingredient.getRecipe().getId() );
        Assert.assertEquals( INGREDIENT_ID, ingredient.getId() );
    }
}
