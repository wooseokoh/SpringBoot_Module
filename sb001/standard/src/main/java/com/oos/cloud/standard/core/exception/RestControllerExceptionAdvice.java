package com.oos.cloud.standard.core.exception;

import com.oos.cloud.standard.core.dto.ShareDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice("com.oos.cloud.standard.controller.api")
public class RestControllerExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ShareDto Exception(HttpServletResponse response, Exception e){
        return new ShareDto(false, e.getMessage());
    }
}
