package com.bishoptod3.controllers;

import com.bishoptod3.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Loky on 12/09/2018.
 */
@Controller
public class RecipeController {

    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/recipe/show/{id}"})
    public String getRecipe(Model model, @PathVariable String id) {

        model.addAttribute( "recipe", recipeService.findById( new Long( id )) );

        return "recipe/show";
    }
}
