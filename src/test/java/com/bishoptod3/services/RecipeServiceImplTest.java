package com.bishoptod3.services;

import com.bishoptod3.converters.RecipeCommandToRecipe;
import com.bishoptod3.converters.RecipeToRecipeCommand;
import com.bishoptod3.domain.Recipe;
import com.bishoptod3.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by Loky on 01/09/2018.
 */
public class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks( this );

        recipeService = new RecipeServiceImpl( recipeRepository, recipeToRecipeCommand, recipeCommandToRecipe);
    }

    @Test
    public void getRecipesTest() throws Exception {

        Recipe recipe = new Recipe();
        Set<Recipe> recipeData = new HashSet<>(  );
        recipeData.add( recipe );

        when(recipeService.getRecipes()).thenReturn( recipeData );

        Set<Recipe> recipeSet = recipeService.getRecipes();
        assertEquals( recipeSet.size(), 1 );

        verify( recipeRepository, times( 1 ) ).findAll();
    }

    @Test
    public void findByIdTest() {

        Long id = 1L;
        Recipe recipe = new Recipe(  );
        recipe.setId( id );

        Optional<Recipe> recipeOptional = Optional.of( recipe );
        Mockito.when( recipeRepository.findById( id )).thenReturn( recipeOptional );

        Recipe recipeReturned = recipeService.findById( id );

        assertNotNull( recipeReturned );
        Mockito.verify( recipeRepository, Mockito.times( 1 ) ).findById( Mockito.anyLong() );
        Mockito.verify( recipeRepository, Mockito.never() ).findAll();


    }

    @Test
    public void deleteByIdTest() throws Exception {

        //given
        Long idToDelete = 1L;

        //when
        recipeService.deleteById(idToDelete);

        //then
        Mockito.verify(recipeRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    public void saveRecipeImageTest() throws Exception {
        //given
        Long id = 1L;
        Recipe recipe = new Recipe(  );
        recipe.setId(id );
        MultipartFile multipartFile = new MockMultipartFile("imagefile",
                "testing.txt", "text/plain", "RecipeProject".getBytes());
        Mockito.when( recipeRepository.findById( Mockito.anyLong() ) ).thenReturn( Optional.of( recipe ) );
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass( Recipe.class );

        //when
        recipeService.saveRecipeImage( id, multipartFile );

        //then
        Mockito.verify( recipeRepository, Mockito.times( 1 ) ).save( argumentCaptor.capture() );
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals( multipartFile.getBytes().length, savedRecipe.getImage().length );

    }

}