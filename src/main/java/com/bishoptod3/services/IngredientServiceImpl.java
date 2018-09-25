package com.bishoptod3.services;

import com.bishoptod3.domain.Ingredient;
import com.bishoptod3.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by DD on 9/24/2018.
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Set<Ingredient> getIngredientsByRecipeId(Long id) {

        if (id == null) throw new IllegalArgumentException("Id value can't be null");

        return ingredientRepository.findAllByRecipeId(id);
    }
}
