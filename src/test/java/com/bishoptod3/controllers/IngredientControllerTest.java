package com.bishoptod3.controllers;


import com.bishoptod3.commands.RecipeCommand;
import com.bishoptod3.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by DD on 9/24/2018.
 */
public class IngredientControllerTest {

    private IngredientController ingredientController;

    @Mock
    private RecipeService recipeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(recipeService);
    }

    @Test
    public void testListIngredients() throws Exception {
        //given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        RecipeCommand command = new RecipeCommand();
        Mockito.when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(command);

        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
        //then
        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(Mockito.anyLong());
    }

}