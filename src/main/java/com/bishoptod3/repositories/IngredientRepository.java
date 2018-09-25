package com.bishoptod3.repositories;

import com.bishoptod3.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

/**
 * Created by DD on 9/24/2018.
 */
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    Set<Ingredient> findAllByRecipeId(Long id);
    Optional<Ingredient> findByRecipeIdAndId(Long recipeId, Long ingredientId);
}
