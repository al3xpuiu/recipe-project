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

        Optional<Ingredient> ingredientOptional = ingredientRepository.findById( command.getId() );
        Ingredient savedIngredient;

        if (ingredientOptional.isPresent()) {
            Ingredient ingredientToEdit = ingredientOptional.get();
            ingredientToEdit.setAmount( detachedIngredient.getAmount() );
            ingredientToEdit.setDescription( detachedIngredient.getDescription() );
            ingredientToEdit.setUom( detachedIngredient.getUom() );
            savedIngredient = ingredientRepository.save( ingredientToEdit );
        } else {
            Optional<Recipe> recipeOptional = recipeRepository.findById( command.getRecipeId() );
            Recipe recipeToBeModified = recipeOptional.orElseThrow( () -> new IllegalArgumentException
                    ( "Can't find any recipe with the id " + command.getRecipeId() ) );
            savedIngredient = detachedIngredient;
            recipeToBeModified.addIngredient( savedIngredient );
            recipeRepository.save( recipeToBeModified );
        }

        return ingredientToIngredientCommand.convert( savedIngredient );
    }
}
