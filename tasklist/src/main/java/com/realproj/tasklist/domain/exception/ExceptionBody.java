package com.realproj.tasklist.domain.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ExceptionBody {

    private String message;
    private Map<String,String> error;

    public ExceptionBody(String message){
        this.message = message;
    }

}
