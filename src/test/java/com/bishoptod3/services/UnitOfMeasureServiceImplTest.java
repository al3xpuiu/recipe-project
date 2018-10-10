package com.bishoptod3.services;

import com.bishoptod3.commands.UnitOfMeasureCommand;
import com.bishoptod3.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.bishoptod3.domain.UnitOfMeasure;
import com.bishoptod3.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Loky on 09/10/2018.
 */
public class UnitOfMeasureServiceImplTest {

    private UnitOfMeasureServiceImpl unitOfMeasureService;
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks( this );
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        unitOfMeasureService = new UnitOfMeasureServiceImpl( unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand );
    }

    @Test
    public void findByIngredientId() throws Exception {

        //given

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId( 2L );
        Optional<UnitOfMeasure> ofMeasureOptional = Optional.of( unitOfMeasure );
        Mockito.when( unitOfMeasureRepository.findByIngredientId( Mockito.anyLong() ) ).thenReturn( ofMeasureOptional );

        //when
        unitOfMeasure = unitOfMeasureService.findByIngredientId( 1L );

        //then

        assertEquals( (Long) 2L, unitOfMeasure.getId() );
    }

    @Test
    public void findAllAndConvertToCommands() throws Exception {

        //given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId( 2L );

        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>(  );
        unitOfMeasures.add( unitOfMeasure );

        Mockito.when( unitOfMeasureRepository.findAll() ).thenReturn( unitOfMeasures );

        //when
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.findAllAndConvertToCommand();

        //then
        assertEquals( 1, unitOfMeasureCommands.size() );

    }

}