package com.bishoptod3.converters;

import com.bishoptod3.commands.CategoryCommand;
import com.bishoptod3.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Loky on 13/09/2018.
 */
public class CategoryCommandToCategoryTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "American";

    private CategoryCommandToCategory converter;

    @Before
    public void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull( converter.convert( null ) );
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull( converter.convert( new CategoryCommand() ) );
    }

    @Test
    public void convert() throws Exception {

        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId( ID );
        categoryCommand.setDescription( DESCRIPTION );

        //when

        Category category = converter.convert( categoryCommand );

        //then
        assertNotNull( category );
        assertEquals( ID , category.getId());
        assertEquals( DESCRIPTION, category.getDescription() );
    }
}