package com.bishoptod3.services;

import com.bishoptod3.commands.IngredientCommand;
import com.bishoptod3.converters.IngredientToIngredientCommand;
import com.bishoptod3.domain.Ingredient;
import com.bishoptod3.repositories.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by DD on 9/24/2018.
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public Set<Ingredient> getIngredientsByRecipeId(Long id) {

        if (id == null) throw new IllegalArgumentException("Id value can't be null");
        log.debug( "Getting all the ingredients with the recipe id of "  + id);
        return ingredientRepository.findAllByRecipeId(id);
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        if (recipeId == null)
            throw new IllegalArgumentException( "RecipeId can't be null" );
        if (ingredientId == null)
            throw new IllegalArgumentException( "IngredientId can't be null" );

        Set<Ingredient> ingredients = ingredientRepository.findAllByRecipeId( recipeId );

        log.debug( "Searching for the ingredient with the id value of " + ingredientId);
        Ingredient ingredient = ingredients.stream()
                .filter( i -> i.getId().equals( ingredientId ))
                .findFirst()
                .orElseThrow( () -> new IllegalArgumentException( "There was no ingredient with the id "  + ingredientId) );


        return ingredientToIngredientCommand.convert( ingredient );
    }
}
