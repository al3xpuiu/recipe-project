package com.bishoptod3.controllers;

import com.bishoptod3.commands.IngredientCommand;
import com.bishoptod3.commands.UnitOfMeasureCommand;
import com.bishoptod3.services.IngredientService;
import com.bishoptod3.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by DD on 9/24/2018.
 */
@Slf4j
@Controller
public class IngredientController {

    private IngredientService ingredientService;
    private UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public IngredientController(IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String getIngredients(@PathVariable String recipeId, Model model) {

        log.debug("Getting ingredient list for recipe id: " + recipeId);

        model.addAttribute("ingredients", ingredientService.getIngredientsByRecipeId(Long.valueOf(recipeId)));
        model.addAttribute( "recipeId", recipeId );
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {

        log.debug( "Getting the ingredient with the id " + id + " that belongs to the recipe with the id " + recipeId );

        model.addAttribute( "ingredient",
                ingredientService.findByRecipeIdAndIngredientId( Long.valueOf( recipeId ), Long.valueOf( id ) ) );

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredientView(@PathVariable String recipeId, @PathVariable String id, Model model) {

        model.addAttribute( "ingredient",
                ingredientService.findByRecipeIdAndIngredientId( Long.valueOf( recipeId ), Long.valueOf( id ) ) );
        model.addAttribute( "uomList", unitOfMeasureService.findAllAndConvertToCommand() );

        return "recipe/ingredient/ingredientForm";
    }


    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {

        log.debug( "Initiating method: ingredientService.saveIngredient(command), in IngredientController" );
        IngredientCommand savedCommand = ingredientService.saveIngredient( command );

        return "redirect:/recipe/" +savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setUom( new UnitOfMeasureCommand() );
        ingredientCommand.setRecipeId( Long.valueOf( recipeId ));
        model.addAttribute( "ingredient", ingredientCommand);

        model.addAttribute( "uomList", unitOfMeasureService.findAllAndConvertToCommand() );

        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id) {
        log.debug( "In IngredientController, deleting ingredient with Id: " + id + " from recipe with Id " + recipeId );
        ingredientService.deleteById( Long.valueOf( id ));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
