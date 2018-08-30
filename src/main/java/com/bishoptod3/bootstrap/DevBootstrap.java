package com.bishoptod3.bootstrap;

import com.bishoptod3.domain.*;
import com.bishoptod3.repositories.CategoryRepository;
import com.bishoptod3.repositories.RecipeRepository;
import com.bishoptod3.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by Loky on 27/08/2018.
 */
@Slf4j
@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public DevBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();
    }

    public void initData() {

        //Categories - start
        Category american = categoryRepository.findByDescription( "American" ).orElseThrow( IllegalArgumentException::new );
        Category italian = categoryRepository.findByDescription( "Italian" ).orElseThrow( IllegalArgumentException::new );
        Category mexican = categoryRepository.findByDescription( "Mexican" ).orElseThrow( IllegalArgumentException::new );
        Category fastFood = categoryRepository.findByDescription( "Fast Food" ).orElseThrow( IllegalArgumentException::new );
        log.debug( "Finished the categories in bootstrap" );
        //Categories - end


        //Units of measure - start
        UnitOfMeasure teaspoon = unitOfMeasureRepository.findByDescription( "Teaspoon" ).orElseThrow( IllegalArgumentException::new );
        UnitOfMeasure tablespoon = unitOfMeasureRepository.findByDescription( "Tablespoon" ).orElseThrow( IllegalArgumentException::new );
        UnitOfMeasure cup = unitOfMeasureRepository.findByDescription( "Cup" ).orElseThrow( IllegalArgumentException::new );
        UnitOfMeasure pinch = unitOfMeasureRepository.findByDescription( "Pinch" ).orElseThrow( IllegalArgumentException::new );
        UnitOfMeasure ounce = unitOfMeasureRepository.findByDescription( "Ounce" ).orElseThrow( IllegalArgumentException::new );
        UnitOfMeasure each = unitOfMeasureRepository.findByDescription( "Each" ).orElseThrow( IllegalArgumentException::new );
        log.debug( "Finished the units of measure in bootstrap" );
        //Units of measure - end

        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.setDescription( "The BEST guacamole! So easy to make with ripe avocados, salt, " +
                "serrano chiles, cilantro and lime. Garnish with red radishes or jicama. Serve with tortilla" +
                " chips. Watch how to make guacamole - it's easy!" );
        perfectGuacamole.setPrepTime( 10 );
        perfectGuacamole.setCookTime( 0 );
        perfectGuacamole.setServings( 4 );
        perfectGuacamole.setSource( "www.simplyrecipes.com" );
        perfectGuacamole.setUrl( "https://www.simplyrecipes.com/recipes/perfect_guacamole/" );
        perfectGuacamole.setDirections( "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed." +
                " Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon." +
                " (See How to Cut and Peel an Avocado.) Place in a bowl.\n 2 Mash with a fork: Using a fork, roughly mash the avocado. " +
                "(Don't overdo it! The guacamole should be a little chunky.)\n 3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. " +
                "The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili" +
                " pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n 4 Cover " +
                "with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. " +
                "(The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve." );

        //Notes -start
        Notes notes = new Notes();
        notes.setRecipeNotes( "All you really need to make guacamole is ripe avocados and salt. After that, a little lime or lemon juice—a splash of acidity—will help to balance the richness of the avocado. Then if you want, add chopped cilantro, chiles, onion, and/or tomato.\n" +
                "\n" +
                "Once you have basic guacamole down, feel free to experiment with variations including strawberries, peaches, pineapple, mangoes, even watermelon. You can get creative with homemade guacamole!\n" +
                "\n" +
                "GUACAMOLE TIP: USE RIPE AVOCADOS\n" +
                "The trick to making perfect guacamole is using ripe avocados that are just the right amount of ripeness. Not ripe enough and the avocado will be hard and tasteless. Too ripe and the taste will be off.\n" +
                "\n" +
                "Check for ripeness by gently pressing the outside of the avocado. If there is no give, the avocado is not ripe yet and will not taste good. If there is a little give, the avocado is ripe. If there is a lot of give, the avocado may be past ripe and not good. In this case, taste test first before using." );
        perfectGuacamole.setNotes( notes );
        //Notes -end

        //Ingredients -start
        Ingredient ingredient = new Ingredient("ripe avocados", new BigDecimal(2), each);
        perfectGuacamole.addIngredient( ingredient );

        ingredient = new Ingredient("Kosher salt", new BigDecimal( 1/2 ), teaspoon);
        perfectGuacamole.addIngredient( ingredient );

        ingredient = new Ingredient("fresh lime juice or lemon juice", new BigDecimal( 1 ), tablespoon);
        perfectGuacamole.addIngredient( ingredient );

        ingredient = new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal( 2 ), tablespoon);
        perfectGuacamole.addIngredient( ingredient );

        ingredient = new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal( 2 ), each);
        perfectGuacamole.addIngredient( ingredient );
        //Ingredients - end

        //Categories - start
        perfectGuacamole.getCategories().add( mexican );
        perfectGuacamole.getCategories().add( american );
        //Categories - end

        perfectGuacamole.setDifficulty( Difficulty.EASY );

        recipeRepository.save( perfectGuacamole );

        log.debug( "I'm at the end of the bootstrap class" );

    }
}
