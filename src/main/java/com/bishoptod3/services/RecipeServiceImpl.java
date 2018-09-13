package com.bishoptod3.services;

import com.bishoptod3.domain.Recipe;
import com.bishoptod3.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Loky on 27/08/2018.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug( "I'm in RecipeServiceImpl" );
        Set<Recipe> recipes = new HashSet<>(  );
        recipeRepository.findAll().forEach( recipes::add );
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        Recipe recipe = recipeRepository.findById( id ).orElse( null );
        if (recipe == null) throw new IllegalArgumentException( "There is no recipe with id: " + id );

        return recipe;
    }
}
