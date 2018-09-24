package com.bishoptod3.controllers;

import com.bishoptod3.commands.RecipeCommand;
import com.bishoptod3.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Loky on 12/09/2018.
 */
@Slf4j
@Controller
public class RecipeController {

    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping({"/recipe/{id}/show"})
    public String getRecipe(Model model, @PathVariable String id) {

        model.addAttribute("recipe", recipeService.findById(new Long(id)));

        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeForm";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/recipeForm";
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/delete")
    public String delete(@PathVariable String id) {

        log.debug("Deleting " + id);

        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }
}
