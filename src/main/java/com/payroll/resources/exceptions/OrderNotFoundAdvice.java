package com.payroll.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A controller advice allows you to use exactly the same exception handling techniques but apply them across the whole application,
 * not just to an individual controller.
 * https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc
 */
@ControllerAdvice
public class OrderNotFoundAdvice {
    @ResponseBody // signals that this advice is rendered straight into the response body.
    @ExceptionHandler(OrderNotFoundException.class) // configures the advice to only respond if an OrderNotFoundException is thrown.
    @ResponseStatus(HttpStatus.NOT_FOUND) // says to issue an HttpStatus.NOT_FOUND, i.e. an HTTP 404.

    String orderNotFoundHandler(OrderNotFoundException ex) {
        // The body of the advice generates the content. In this case, it gives the message of the exception.
        return ex.getMessage();
    }
}
