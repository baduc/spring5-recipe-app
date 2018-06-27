package guru.springframework.controllers;

import guru.springframework.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception ex) {
        log.error("Handling NotFoundException");
        log.error(ex.getMessage());
        ModelAndView mav = new ModelAndView("404error");
        mav.addObject("exception", ex);
        return mav;
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNumberFormat(Exception ex) {
        log.error("Handling NumberFormatException");
        log.error(ex.getMessage());
        ModelAndView mav = new ModelAndView("400error");
        mav.addObject("exception", ex);
        return mav;
    }
}
