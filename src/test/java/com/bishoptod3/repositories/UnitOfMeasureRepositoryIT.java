package com.bishoptod3.repositories;

import com.bishoptod3.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Created by Loky on 02/09/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByDescription() throws Exception {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription( "Teaspoon" );
        assertEquals( "Teaspoon", unitOfMeasureOptional.orElse( null ).getDescription() );
    }

    @Test
    public void findByIngredientId() throws Exception {
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByIngredientId( 1L );
        assertEquals( true, unitOfMeasureOptional.isPresent() );
    }

}