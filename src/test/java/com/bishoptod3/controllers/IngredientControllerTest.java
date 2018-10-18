package com.bishoptod3.controllers;


import com.bishoptod3.commands.IngredientCommand;
import com.bishoptod3.commands.UnitOfMeasureCommand;
import com.bishoptod3.domain.Ingredient;
import com.bishoptod3.services.IngredientService;
import com.bishoptod3.services.UnitOfMeasureService;
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

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(ingredientService, unitOfMeasureService );
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

    @Test
    public void showIngredientTest() throws Exception {
        //given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup( ingredientController ).build();
        IngredientCommand command = new IngredientCommand();

        //when
        Mockito.when( ingredientService.findByRecipeIdAndIngredientId( Mockito.anyLong(), Mockito.anyLong() ))
                .thenReturn( command );

        //then
        mockMvc.perform( MockMvcRequestBuilders.get( "/recipe/1/ingredient/1/show" ) )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.view().name( "recipe/ingredient/show" ) )
                .andExpect( MockMvcResultMatchers.model().attributeExists( "ingredient" ) );
    }

    @Test
    public void updateRecipeIngredientViewTest() throws Exception {
        //given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup( ingredientController ).build();
        IngredientCommand command = new IngredientCommand();
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>(  );
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommands.add( unitOfMeasureCommand );

        //when
        Mockito.when( ingredientService.findByRecipeIdAndIngredientId( Mockito.anyLong(), Mockito.anyLong() ))
                .thenReturn( command );
        Mockito.when( unitOfMeasureService.findAllAndConvertToCommand() ).thenReturn( unitOfMeasureCommands );

        //then

        mockMvc.perform( MockMvcRequestBuilders.get( "/recipe/1/ingredient/1/update" ) )
                .andExpect( MockMvcResultMatchers.status().isOk() )
                .andExpect( MockMvcResultMatchers.view().name( "recipe/ingredient/ingredientForm" ) )
                .andExpect( MockMvcResultMatchers.model().attributeExists( "ingredient" ) )
                .andExpect( MockMvcResultMatchers.model().attributeExists( "uomList" ) );
    }

    @Test
    public void newRecipeIngredientViewTest() throws Exception {
        //given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup( ingredientController ).build();

        //when

        //then
        mockMvc.perform( MockMvcRequestBuilders.get( "/recipe/1/ingredient/new" ) )
                .andExpect(MockMvcResultMatchers.model().attributeExists( "ingredient" ) )
                .andExpect( MockMvcResultMatchers.view().name( "recipe/ingredient/ingredientForm" ) )
                .andExpect( MockMvcResultMatchers.status().isOk() );
    }

    @Test
    public void deleteIngredientByIdTest() throws Exception {
        //given
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup( ingredientController ).build();

        //then
        mockMvc.perform( MockMvcRequestBuilders.get( "/recipe/1/ingredient/1/delete" ) )
                .andExpect( MockMvcResultMatchers.status().is3xxRedirection() )
                .andExpect( MockMvcResultMatchers.view().name( "redirect:/recipe/1/ingredients" ) );

        Mockito.verify( ingredientService, Mockito.times( 1 ) ).deleteById( Mockito.anyLong() );
    }
}