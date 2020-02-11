package com.project.webflux.handle;


import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

public class ControllerAdvice extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
                                                  boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(
                request, includeStackTrace);
        map.put("status", HttpStatus.BAD_REQUEST);
        return map;
    }
}
