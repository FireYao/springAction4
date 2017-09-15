package com.fireyao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by lly on 2017/9/1
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "resources not found")
public class BeanNotFoundException extends RuntimeException {
}
