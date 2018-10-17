package com.bishoptod3.services;

import com.bishoptod3.commands.IngredientCommand;
import com.bishoptod3.converters.IngredientCommandToIngredient;
import com.bishoptod3.converters.IngredientToIngredientCommand;
import com.bishoptod3.domain.Ingredient;
import com.bishoptod3.domain.Recipe;
import com.bishoptod3.repositories.IngredientRepository;
import com.bishoptod3.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

/**
 * Created by DD on 9/24/2018.
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;
    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private RecipeRepository recipeRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Ingredient> getIngredientsByRecipeId(Long id) {

        if (id == null) throw new IllegalArgumentException("Id value can't be null");
        log.debug( "Getting all the ingredients with the recipe id of "  + id);
        return ingredientRepository.findAllByRecipeId(id);
    }

    @Override
    @Transactional
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        if (recipeId == null)
            throw new IllegalArgumentException( "RecipeId can't be null" );

        if (ingredientId == null)
            throw new IllegalArgumentException( "IngredientId can't be null" );

        log.debug( "Finding all ingredients with the recipeId of: " + recipeId );
        Set<Ingredient> ingredients = ingredientRepository.findAllByRecipeId( recipeId );

        log.debug( "Searching for the ingredient with the id value of " + ingredientId);
        Ingredient ingredient = ingredients.stream()
                .filter( i -> i.getId().equals( ingredientId ))
                .findFirst()
                .orElseThrow( () -> new IllegalArgumentException( "There was no ingredient with the id "  + ingredientId) );


        return ingredientToIngredientCommand.convert( ingredient );
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredient(IngredientCommand command) {

        if (command == null)
            throw new IllegalArgumentException( "IngredientCommand can't be null" );

        Ingredient detachedIngredient = ingredientCommandToIngredient.convert( command );

        if (detachedIngredient == null)
            throw new IllegalArgumentException( "detachedIngredient can't be null" );

        Optional<Ingredient> ingredientFromRepository = Optional.empty();
        if (command.getId() != null)
            ingredientFromRepository = ingredientRepository.findById( command.getId() );

        Ingredient savedOrUpdatedIngredient;

        if (ingredientFromRepository.isPresent())
            savedOrUpdatedIngredient = updateAndGetIngredientFromRepository( ingredientFromRepository.get(), detachedIngredient );
        else {
            savedOrUpdatedIngredient = saveAndGetIngredientForRecipe( command.getRecipeId(), detachedIngredient );
        }

        return ingredientToIngredientCommand.convert( savedOrUpdatedIngredient );
    }

    @Transactional
    private Ingredient updateAndGetIngredientFromRepository(Ingredient ingredientToUpdate, Ingredient ingredientFromForm) {

        ingredientToUpdate.setAmount( ingredientFromForm.getAmount() );
        ingredientToUpdate.setDescription( ingredientFromForm.getDescription() );
        ingredientToUpdate.setUom( ingredientFromForm.getUom() );

        return ingredientRepository.save( ingredientToUpdate );

    }

    @Transactional
    private Ingredient saveAndGetIngredientForRecipe(Long recipeId, Ingredient ingredientFromForm) {

        Optional<Recipe> recipeOptional = recipeRepository.findById( recipeId );
        Recipe recipeToBeModified = recipeOptional.orElseThrow( () -> new IllegalArgumentException
                ( "Can't find any recipe with the id " + recipeId ) );
        ingredientFromForm.setRecipe( recipeToBeModified );
        recipeToBeModified.addIngredient( ingredientFromForm );
        Recipe savedRecipe = recipeRepository.save( recipeToBeModified );

        return getSavedIngredient(savedRecipe, ingredientFromForm);
    }

    @Transactional
    private Ingredient getSavedIngredient(Recipe savedRecipe, Ingredient savedIngredient) {
        log.debug( "I'm in getSavedIngredient(). " );
        return savedRecipe.getIngredients()
                .stream()
                .filter( i -> i.getAmount().equals( savedIngredient.getAmount() ) )
                .filter( i -> i.getDescription().equals( savedIngredient.getDescription() ) )
                .filter( i -> i.getUom().getId().equals( savedIngredient.getUom().getId()) )
                .findFirst().orElseThrow( () -> new IllegalArgumentException( "The value can't be null" ) );
    }

}
