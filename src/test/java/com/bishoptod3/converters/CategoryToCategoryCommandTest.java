package com.bishoptod3.converters;

import com.bishoptod3.domain.Category;
import org.junit.Before;

/**
 * Created by Loky on 13/09/2018.
 */
public class CategoryToCategoryCommandTest {

    private Category category;

    private final static Long ID = 1L;
    private final static String DESCRIPTION = "MEXICAN";

    @Before
    public void setUp() {
        category = new Category();
    }

}