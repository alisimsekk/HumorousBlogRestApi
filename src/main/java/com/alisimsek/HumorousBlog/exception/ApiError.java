package com.alisimsek.HumorousBlog.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(value = JsonInclude.Include.NON_NULL) //response'da json a dönerken null olmayan alanları dönecek, null değerler gösterilmeyecek
public class ApiError {
    private Integer statusCode;
    private String message;
    private String path;
    private Long occurrenceDate = new Date().getTime();
    private Map<String,String> valiadationErrors = null;
}
