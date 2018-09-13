package com.bishoptod3.converters;

import com.bishoptod3.commands.CategoryCommand;
import com.bishoptod3.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by Loky on 13/09/2018.
 */
@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(@Nullable Category source) {

        if (source == null)
            return null;

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId( source.getId() );
        categoryCommand.setDescription( source.getDescription());

        return categoryCommand;
    }
}
