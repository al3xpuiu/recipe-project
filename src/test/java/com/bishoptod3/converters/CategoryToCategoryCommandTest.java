package com.bishoptod3.converters;

import com.bishoptod3.commands.CategoryCommand;
import com.bishoptod3.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Loky on 13/09/2018.
 */
public class CategoryToCategoryCommandTest {

    private CategoryToCategoryCommand converter;

    private final static Long ID = 1L;
    private final static String DESCRIPTION = "MEXICAN";

    @Before
    public void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testNotNullObject() {
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    public void  testConverter() {

        //given
        Category category = new Category();
        category.setId(ID);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = converter.convert(category);

        //then
        assertNotNull(categoryCommand);
        assertEquals(ID, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());

    }
}