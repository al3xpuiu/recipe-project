package com.bishoptod3.controllers;

import com.bishoptod3.commands.RecipeCommand;
import com.bishoptod3.domain.Recipe;
import com.bishoptod3.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by Loky on 12/09/2018.
 */

public class RecipeControllerTest {

    private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks( this );
        recipeController = new RecipeController( recipeService );
    }

    @Test
    public void getRecipeTest() throws Exception {

        Recipe recipe = Recipe.builder().id( 1L ).build();

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup( recipeController ).build();

        Mockito.when( recipeService.findById( Mockito.anyLong()) ).thenReturn( recipe );

        mockMvc.perform( MockMvcRequestBuilders.get( "/recipe/1/show" ))
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.view().name( "recipe/show" ) )
                .andExpect( MockMvcResultMatchers.model().attributeExists( "recipe" ) );

    }

    @Test
    public void saveOrUpdateTest() throws Exception {

        RecipeCommand command = new RecipeCommand();
        command.setId(2L);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        Mockito.when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "same new description"))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/2/show"));


    }

    @Test
    public void newRecipeViewTest() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    public void updateRecipeViewTest() throws Exception {

        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        Mockito.when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeForm"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }



}