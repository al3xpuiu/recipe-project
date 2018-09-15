package com.bishoptod3.converters;

import com.bishoptod3.commands.UnitOfMeasureCommand;
import com.bishoptod3.domain.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Loky on 13/09/2018.
 */
public class UnitOfMeasureToUnitOfMeasureCommandTest {

    private UnitOfMeasureToUnitOfMeasureCommand converter;
    private static final Long ID = 1L;
    private static final String DESCRIPTION = "Some description";

    @Before
    public void setUp() {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObject() {
        Assert.assertNull(converter.convert(null));
    }

    @Test
    public void testNotNullObject() {
        Assert.assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void testConverter() {

        //given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID);
        unitOfMeasure.setDescription(DESCRIPTION);

        //when
        UnitOfMeasureCommand unitOfMeasureCommand = converter.convert(unitOfMeasure);

        //then
        Assert.assertNotNull(unitOfMeasureCommand);
        Assert.assertEquals(ID, unitOfMeasureCommand.getId());
        Assert.assertEquals(DESCRIPTION, unitOfMeasureCommand.getDescription());
    }

}