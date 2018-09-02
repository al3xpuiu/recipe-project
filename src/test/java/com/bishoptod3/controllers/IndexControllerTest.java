package com.bishoptod3.controllers;

import com.bishoptod3.domain.Recipe;
import com.bishoptod3.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Loky on 01/09/2018.
 */
public class IndexControllerTest {

    private IndexController indexController;

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks( this );
        indexController = new IndexController( recipeService );
    }

    @Test
    public void getIndexPage() throws Exception {

        //given
        Set<Recipe> recipes = new HashSet<>(  );
        recipes.add( Recipe.builder().id( 1L ).build() );
        recipes.add( Recipe.builder().id( 2L ).build() );
        when( recipeService.getRecipes() ).thenReturn( recipes );

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass( Set.class );

        //when
        String indexPageString = indexController.getIndexPage( model );

        //then
        assertEquals( indexPageString, "index" );
        verify( model, times( 1 ) ).addAttribute( eq("recipes"), argumentCaptor.capture() );
        verify( recipeService, times( 1 ) ).getRecipes();
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals( 2, setInController.size() );

    }

}