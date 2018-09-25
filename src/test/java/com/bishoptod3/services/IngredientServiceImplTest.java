package com.bishoptod3.services;

import com.bishoptod3.domain.Ingredient;
import com.bishoptod3.domain.Recipe;
import com.bishoptod3.repositories.IngredientRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DD on 9/24/2018.
 */
public class IngredientServiceImplTest {

    private IngredientServiceImpl ingredientService;

    private static final Long RECIPE_ID=1L;

    @Mock
    private IngredientRepository ingredientRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientRepository);
    }

    @Test
    public void getIngredientsByRecipeIdTest() throws Exception {

        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient ingredient = new Ingredient();
        ingredient.setRecipe(recipe);
        ingredients.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setRecipe(recipe);
        ingredients.add(ingredient);
        Mockito.when(ingredientRepository.findAllByRecipeId(Mockito.anyLong())).thenReturn(ingredients);

        //when
        Set<Ingredient> ingredientSet = ingredientService.getIngredientsByRecipeId(RECIPE_ID);

        //then
        Mockito.verify(ingredientRepository,
                Mockito.times(1)).findAllByRecipeId(Mockito.anyLong());

    }

}