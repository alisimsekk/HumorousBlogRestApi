package com.alisimsek.HumorousBlog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private Integer statusCode;
    private String message;
    private String path;
    private Long occurrenceDate = new Date().getTime();
    private Map<String,String> valiadationErrors = new HashMap<>();
}
