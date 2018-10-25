package com.bishoptod3.controllers;

import com.bishoptod3.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Loky on 25/10/2018.
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception notFoundException) {
        return modelAndView( "404error", notFoundException );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView numberFormatException(Exception numberFormatException) {
        return modelAndView( "400error", numberFormatException );
    }

    private ModelAndView modelAndView(String viewName, Exception exception) {
        log.error( exception.getMessage());

        ModelAndView modelAndView = new ModelAndView(  );
        modelAndView.setViewName( viewName );
        modelAndView.addObject( "exception", exception );

        return modelAndView;
    }
}
