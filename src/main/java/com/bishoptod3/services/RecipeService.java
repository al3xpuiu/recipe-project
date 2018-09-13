package com.bishoptod3.services;

import com.bishoptod3.domain.Recipe;

import java.util.Set;

/**
 * Created by Loky on 27/08/2018.
 */
public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);
}
