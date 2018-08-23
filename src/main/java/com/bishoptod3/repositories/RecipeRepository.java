package com.bishoptod3.repositories;

import com.bishoptod3.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Loky on 23/08/2018.
 */
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
