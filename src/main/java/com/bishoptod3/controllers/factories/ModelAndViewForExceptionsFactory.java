package com.bishoptod3.controllers.factories;

import com.bishoptod3.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Loky on 24/10/2018.
 */
@Slf4j
public class ModelAndViewForExceptionsFactory {

    public static ModelAndView getModelAndViewForException(Exception exception) {

        if (exception instanceof NotFoundException) {
            return modelAndView( "404error", exception );
        }

        if (exception instanceof NumberFormatException) {
            return modelAndView("400error", exception);
        }

        throw new IllegalArgumentException( );
    }

    private static ModelAndView modelAndView(String viewName, Exception exception) {

        log.error( exception.getMessage());

        ModelAndView modelAndView = new ModelAndView(  );
        modelAndView.setViewName( viewName );
        modelAndView.addObject( "exception", exception );

        return modelAndView;
    }
}
