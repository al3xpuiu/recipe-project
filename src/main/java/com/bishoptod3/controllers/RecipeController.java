package com.bishoptod3.controllers;

import com.bishoptod3.commands.RecipeCommand;
import com.bishoptod3.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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

    @GetMapping("/recipe/{id}/show")
    public String getRecipe(Model model, @PathVariable String id) {

        model.addAttribute("recipe", recipeService.findById(new Long(id)));

        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeForm";
    }

    @PostMapping("recipe")
    public String saveOrUpdateRecipe(@Valid @ModelAttribute("recipe") RecipeCommand command,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach( error -> log.debug( error.toString() ) );
            return "recipe/recipeForm";
        }

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/recipeForm";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {

        log.debug("Deleting " + id);

        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }

    @GetMapping("/recipe/{id}/image/change")
    public String showUploadForm(@PathVariable String id, Model model) {

        log.debug( "In RecipeController, method showUploadForm(). Finding recipe by id. Id=" + id );

        model.addAttribute( "recipe", recipeService.findCommandById( Long.valueOf( id )) );
        return "/recipe/imageUploadForm";
    } 

    @PostMapping("/recipe/{id}/image")
    public String changeImage(@PathVariable String id, @RequestParam("imagefile") MultipartFile multipartFile) {

        log.debug( "In RecipeController. Calling method saveRecipeImage()" );
        recipeService.saveRecipeImage( Long.valueOf( id ), multipartFile );
        return "redirect:/recipe/" + id + "/show";

    }

    @GetMapping("/recipe/{id}/recipeImage")
    public void renderImageFromDb(@PathVariable String id, HttpServletResponse response) throws IOException {

        RecipeCommand recipeCommand = recipeService.findCommandById( Long.valueOf( id ) );
        if (recipeCommand.getImage() != null) {
            byte[] imageInBytes = getImageInPrimitiveBytes( recipeCommand );
            writeImageDataInResponse( imageInBytes, response );
        }

    }

    private byte[] getImageInPrimitiveBytes(RecipeCommand recipeCommand) {

        byte[] imageInBytes = new byte[recipeCommand.getImage().length];
        int i = 0;
        for (Byte b: recipeCommand.getImage()) {
            imageInBytes[i++]=b;
        }
        return imageInBytes;
    }

    private void writeImageDataInResponse(byte[] imageInBytes, HttpServletResponse response) throws IOException{
        try(InputStream inputStream = new ByteArrayInputStream( imageInBytes )) {
            response.setContentType( "image/jpeg" );
            IOUtils.copy( inputStream, response.getOutputStream() );
        }
    }

}
