package com.bishoptod3.commands;

import com.bishoptod3.domain.Category;
import com.bishoptod3.domain.Difficulty;
import com.bishoptod3.domain.Ingredient;
import com.bishoptod3.domain.Notes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Loky on 13/09/2018.
 */
@Setter
@Getter
@NoArgsConstructor
public class RecipeCommand {

    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<Ingredient> ingredients = new HashSet<>(  );
    private Byte[] image;
    private Notes notes;
    private Difficulty difficulty;
    private Set<Category> categories = new HashSet<>(  );

}
