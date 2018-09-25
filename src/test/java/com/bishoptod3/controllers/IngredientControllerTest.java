package com.bishoptod3.controllers;


import com.bishoptod3.domain.Ingredient;
import com.bishoptod3.services.IngredientService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DD on 9/24/2018.
 */
public class IngredientControllerTest {

    private IngredientController ingredientController;

    @Mock
    private IngredientService ingredientService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(ingredientService);
    }

    @Test
    public void testListIngredients() throws Exception {
        //given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient ingredient = new Ingredient();
        ingredients.add(ingredient);
        ingredient = new Ingredient();
        ingredients.add(ingredient);
        Mockito.when(ingredientService.getIngredientsByRecipeId(Mockito.anyLong())).thenReturn(ingredients);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredients"));

        //then
        Mockito.verify(ingredientService, Mockito.times(1))
                .getIngredientsByRecipeId(Mockito.anyLong());
    }

}