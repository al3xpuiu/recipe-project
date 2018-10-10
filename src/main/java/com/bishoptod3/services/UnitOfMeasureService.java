package com.bishoptod3.services;

import com.bishoptod3.commands.UnitOfMeasureCommand;
import com.bishoptod3.domain.UnitOfMeasure;

import java.util.Set;

/**
 * Created by Loky on 09/10/2018.
 */
public interface UnitOfMeasureService {

    UnitOfMeasure findByIngredientId(Long id);
    Set<UnitOfMeasureCommand> findAllAndConvertToCommand();
}
