package com.bishoptod3.repositories;

import com.bishoptod3.domain.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Loky on 23/08/2018.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
