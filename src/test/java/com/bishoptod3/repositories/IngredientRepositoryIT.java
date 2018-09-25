package com.bishoptod3.repositories;

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
 * Created by DD on 9/24/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IngredientRepositoryIT {

    private static final Long RECIPE_ID = 1L;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    @Transactional
    public void findAllByRecipeIdTest() {

        //given
        Set<Ingredient> ingredients = ingredientRepository.findAllByRecipeId(RECIPE_ID);

        //when

        //then
        Assert.assertNotNull(ingredients);
        Assert.assertEquals(5, ingredients.size());


    }
}
