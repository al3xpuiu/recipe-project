package com.bishoptod3.services;

import com.bishoptod3.commands.RecipeCommand;
import com.bishoptod3.domain.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * Created by Loky on 27/08/2018.
 */
public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);

    Byte[] saveRecipeImage(Long recipeId, MultipartFile multipartFile);
}
