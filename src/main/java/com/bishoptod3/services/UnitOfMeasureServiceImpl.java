package com.bishoptod3.services;

import com.bishoptod3.commands.UnitOfMeasureCommand;
import com.bishoptod3.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.bishoptod3.domain.UnitOfMeasure;
import com.bishoptod3.exceptions.NotFoundException;
import com.bishoptod3.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Loky on 09/10/2018.
 */
@Service
@Slf4j
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private UnitOfMeasureRepository unitOfMeasureRepository;
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Autowired
    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public UnitOfMeasure findByIngredientId(Long id) {
        if (id == null) throw new IllegalArgumentException( "Id can't be null" );

        log.debug( "Searching for UnitOfMeasure by Ingredient Id: " + id );
        return unitOfMeasureRepository.findByIngredientId( id )
                .orElseThrow( () -> new NotFoundException( "Can't find a unit of measure obj belonging to" +
                        " and ingredient obj with the id: " + id ) );
    }

    @Override
    public Set<UnitOfMeasureCommand> findAllAndConvertToCommand() {
        Set<UnitOfMeasureCommand> unitOfMeasures = new HashSet<>(  );
        log.debug( "Searching all unitOfMeasures obj, then converting them to commands." );
        unitOfMeasureRepository.findAll()
                .forEach( u -> unitOfMeasures.add( unitOfMeasureToUnitOfMeasureCommand.convert( u )) );

        return unitOfMeasures;
    }
}
