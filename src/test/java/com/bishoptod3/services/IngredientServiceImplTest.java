package com.bishoptod3.services;

import com.bishoptod3.commands.IngredientCommand;
import com.bishoptod3.converters.IngredientCommandToIngredient;
import com.bishoptod3.converters.IngredientToIngredientCommand;
import com.bishoptod3.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.bishoptod3.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.bishoptod3.domain.Ingredient;
import com.bishoptod3.domain.Recipe;
import com.bishoptod3.repositories.IngredientRepository;
import com.bishoptod3.repositories.RecipeRepository;
import org.junit.Assert;
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
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    private static final Long INGREDIENT_ID_1 = 1L;
    private static final Long INGREDIENT_ID_2 = 2L;

    private static final Long RECIPE_ID=1L;

    private static final Long UNIT_OF_MEASURE_ID=1L;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Mock
    private UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    @Mock
    private RecipeRepository recipeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientToIngredientCommand = new IngredientToIngredientCommand( unitOfMeasureToUnitOfMeasureCommand );
        ingredientCommandToIngredient = new IngredientCommandToIngredient( unitOfMeasureCommandToUnitOfMeasure );
        ingredientService = new IngredientServiceImpl(ingredientRepository, ingredientToIngredientCommand, ingredientCommandToIngredient, recipeRepository );

    }

    @Test
    public void getIngredientsByRecipeIdTest() throws Exception {

        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient ingredient = new Ingredient();
        ingredient.setRecipe(recipe);
        ingredient.setDescription( "Some des" );
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
        Assert.assertEquals(2, ingredientSet.size());
    }

    @Test
    public void findByRecipeIdAndIngredientId() throws Exception {
        //given
        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient ingredient = new Ingredient();
        ingredient.setId( INGREDIENT_ID_1 );
        ingredients.add(ingredient);
        ingredient = new Ingredient();
        ingredient.setId( INGREDIENT_ID_2 );
        ingredients.add(ingredient);

        Mockito.when(ingredientRepository.findAllByRecipeId(Mockito.anyLong())).thenReturn(ingredients);

        //when
        IngredientCommand command = ingredientService.findByRecipeIdAndIngredientId( RECIPE_ID, INGREDIENT_ID_2 );

        //then
        Mockito.verify( ingredientRepository, Mockito.times( 1 ) )
                .findAllByRecipeId( Mockito.anyLong() );

        Assert.assertNotNull( command );
        Assert.assertEquals( INGREDIENT_ID_2, command.getId() );
    }

    @Test
    public void deleteByIdTest() throws Exception {

        //when
        ingredientService.deleteById(INGREDIENT_ID_1);

        //then
        Mockito.verify(ingredientRepository, Mockito.times( 1 )).deleteById( Mockito.anyLong() );

    }
}