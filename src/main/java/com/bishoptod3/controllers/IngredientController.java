package com.bishoptod3.controllers;

import com.bishoptod3.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by DD on 9/24/2018.
 */
@Slf4j
@Controller
public class IngredientController {

    private IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String getIngredients(@PathVariable String recipeId, Model model) {

        log.debug("Getting ingredient list for recipe id: " + recipeId);

        model.addAttribute("ingredients", ingredientService.getIngredientsByRecipeId(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String getIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {

        log.debug( "Getting the ingredient with the id " + id + " that belongs to the recipe with the id " + recipeId );

        model.addAttribute( "ingredient",
                ingredientService.findByRecipeIdAndIngredientId( Long.valueOf( recipeId ), Long.valueOf( id ) ) );

        return "recipe/ingredient/show";
    }

}
