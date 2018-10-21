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
        Ingredient ingredientFromWebsite = validateAndConvertCommandToIngredient( command );
        Optional<Ingredient> ingredientOptionalFromRepository = findIngredientOptionalByCommandId( command.getId() );
        Ingredient ingredientSavedOrUpdated;
        if (ingredientOptionalFromRepository.isPresent()) {
            Ingredient ingredientToUpdate = ingredientOptionalFromRepository.get();
            ingredientSavedOrUpdated = updateIngredientFromRepository( ingredientToUpdate, ingredientFromWebsite );
        } else {
            ingredientSavedOrUpdated = saveIngredientForRecipe( command.getRecipeId(), ingredientFromWebsite );
        }
        return ingredientToIngredientCommand.convert( ingredientSavedOrUpdated );
    }

    private Ingredient validateAndConvertCommandToIngredient(IngredientCommand command) {
        if (command == null)
            throw new IllegalArgumentException( "IngredientCommand can't be null" );
        Ingredient ingredientFromForm = ingredientCommandToIngredient.convert( command );
        if (ingredientFromForm == null)
            throw new IllegalArgumentException( "ingredientFromForm can't be null" );
        return ingredientFromForm;
    }

    private Optional<Ingredient> findIngredientOptionalByCommandId(Long id) {
        Optional<Ingredient> ingredientOptionalFromRepository = Optional.empty();
        if (id != null)
            ingredientOptionalFromRepository = ingredientRepository.findById( id );
        return ingredientOptionalFromRepository;
    }

    @Transactional
    private Ingredient updateIngredientFromRepository(Ingredient ingredientToUpdate, Ingredient ingredientFromForm) {
        ingredientToUpdate.setAmount( ingredientFromForm.getAmount() );
        ingredientToUpdate.setDescription( ingredientFromForm.getDescription() );
        ingredientToUpdate.setUom( ingredientFromForm.getUom() );
        return ingredientRepository.save( ingredientToUpdate );

    }

    @Transactional
    private Ingredient saveIngredientForRecipe(Long recipeId, Ingredient ingredientFromForm) {
        Recipe recipeToBeModified = getRecipeThatNeedsToBeModified( recipeId );
        ingredientFromForm.setRecipe( recipeToBeModified );
        recipeToBeModified.addIngredient( ingredientFromForm );
        Recipe savedRecipe = recipeRepository.save( recipeToBeModified );
        return getSavedIngredient(savedRecipe, ingredientFromForm);
    }

    private Recipe getRecipeThatNeedsToBeModified(Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById( recipeId );
        return recipeOptional.orElseThrow( () -> new IllegalArgumentException
                ( "Can't find any recipe with the id " + recipeId ) );
    }

    @Transactional
    private Ingredient getSavedIngredient(Recipe savedRecipe, Ingredient savedIngredient) {
        log.debug( "I'm in getSavedIngredient(). " );
        return savedRecipe.getIngredients()
                .stream()
                .filter( i -> i.getAmount().equals( savedIngredient.getAmount() ) )
                .filter( i -> i.getDescription().equals( savedIngredient.getDescription() ) )
                .filter( i -> i.getUom().getId().equals( savedIngredient.getUom().getId()) )
                .findFirst().orElseThrow( () -> new IllegalArgumentException( "The value can't be found" ) );
    }

    @Override
    public void deleteById(Long id) {
        log.debug( "I'm trying to deleteRecipe ingredient with the id: " + id );
        ingredientRepository.deleteById( id );
    }
}
