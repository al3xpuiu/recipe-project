package com.bishoptod3.converters;

import com.bishoptod3.commands.UnitOfMeasureCommand;
import com.bishoptod3.domain.UnitOfMeasure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Loky on 13/09/2018.
 */
public class UnitOfMeasureCommandToUnitOfMeasureTest {

    private UnitOfMeasureCommandToUnitOfMeasure converter;
    private static final Long ID = 1L;
    private static final String DESCRIPTION = "Some description";

    @Before
    public void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullObject() {
        Assert.assertNull(converter.convert(null));
    }

    @Test
    public void testNotNullObject() {
        Assert.assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void testConverter() {

        //given
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID);
        unitOfMeasureCommand.setDescription(DESCRIPTION);

        //when
        UnitOfMeasure unitOfMeasure = converter.convert(unitOfMeasureCommand);

        //then
        Assert.assertNotNull(unitOfMeasure);
        Assert.assertEquals(ID, unitOfMeasure.getId());
        Assert.assertEquals(DESCRIPTION, unitOfMeasure.getDescription());
    }
}