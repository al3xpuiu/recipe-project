package com.bishoptod3.services;

import com.bishoptod3.commands.RecipeCommand;
import com.bishoptod3.converters.RecipeCommandToRecipe;
import com.bishoptod3.converters.RecipeToRecipeCommand;
import com.bishoptod3.domain.Recipe;
import com.bishoptod3.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Loky on 27/08/2018.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;
    private RecipeToRecipeCommand recipeToRecipeCommand;
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
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

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);

        Recipe savedRecipe = null;
        if (detachedRecipe != null)
            savedRecipe = recipeRepository.save(detachedRecipe);


        if (savedRecipe != null) {
            log.debug("Saved recipe " + savedRecipe.getId());
        } else {
            log.debug("Recipe was null. Couldn't save recipeCommand.");
        }

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {

        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe == null) throw new IllegalArgumentException("There is not recipe with the id " + id);

        return recipeToRecipeCommand.convert(recipe);
    }

    @Override
    public void deleteById(Long id) {

        if (id == null) throw new IllegalArgumentException("Id can't be null");
        recipeRepository.deleteById(id);

    }

    @Override
    @Transactional
    public Byte[] saveRecipeImage(Long recipeId, MultipartFile multipartFile) {

        log.debug( "In RecipeService. Saving image on recipe with id " + recipeId );

        Recipe recipeToBeModified = getRecipeFromRepository( recipeId );
        Byte[] image = getImageAsByteArrayFromMultipartfile( multipartFile );
        recipeToBeModified.setImage( image );
        recipeRepository.save( recipeToBeModified );

        return image;
    }

    private Recipe getRecipeFromRepository(Long recipeId) {
        return recipeRepository.findById( recipeId )
                .orElseThrow( () -> new IllegalArgumentException( "Recipe with id " + recipeId + " was not found" ) );
    }

    private Byte[] getImageAsByteArrayFromMultipartfile(MultipartFile multipartFile) {

        Byte[] image = null;
        try {
            byte[] multipartFileBytes = multipartFile.getBytes();
            int length = multipartFileBytes.length;
            log.debug( "Byte length in multipartFile: " + length );
            image = new Byte[length];
            for(int i=0; i<length; i++) {
                image[i] = multipartFileBytes[i];
            }
            log.debug( "Byte length in image after copy: " + image.length );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
