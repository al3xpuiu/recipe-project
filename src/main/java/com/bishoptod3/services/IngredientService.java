package com.bishoptod3.services;

import com.bishoptod3.domain.Ingredient;

import java.util.Set;

/**
 * Created by DD on 9/24/2018.
 */
public interface IngredientService {

    Set<Ingredient> getIngredientsByRecipeId(Long id);
}
