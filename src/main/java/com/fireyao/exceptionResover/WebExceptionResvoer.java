package com.fireyao.exceptionResover;

import com.fireyao.exception.BeanNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lly on 2017/9/1
 */
@ControllerAdvice
public class WebExceptionResvoer {

    @ExceptionHandler(BeanNotFoundException.class)
    @ResponseBody
    public String notFound(BeanNotFoundException e) {
        ResponseStatus annotation = e.getClass().getAnnotation(ResponseStatus.class);
        return annotation.reason();
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPoint() {
        return "404";
    }
}
