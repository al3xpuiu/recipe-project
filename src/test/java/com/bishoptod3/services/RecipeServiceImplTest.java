package com.bishoptod3.services;

import com.bishoptod3.domain.Recipe;
import com.bishoptod3.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Loky on 01/09/2018.
 */
public class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks( this );

        recipeService = new RecipeServiceImpl( recipeRepository );
    }

    @Test
    public void getRecipes() throws Exception {

        Recipe recipe = new Recipe();
        Set<Recipe> recipeData = new HashSet<>(  );
        recipeData.add( recipe );

        when(recipeService.getRecipes()).thenReturn( recipeData );

        Set<Recipe> recipeSet = recipeService.getRecipes();
        assertEquals( recipeSet.size(), 1 );

        verify( recipeRepository, times( 1 ) ).findAll();
    }

}