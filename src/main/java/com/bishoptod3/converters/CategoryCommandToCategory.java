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
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category>{


    @Synchronized
    @Nullable
    @Override
    public Category convert(@Nullable CategoryCommand source) {

        if (source == null)
            return null;

        final Category category = new Category();
        category.setId( source.getId() );
        category.setDescription( source.getDescription() );

        return category;
    }
}
