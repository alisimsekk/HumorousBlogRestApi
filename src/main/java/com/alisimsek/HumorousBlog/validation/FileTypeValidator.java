package com.alisimsek.HumorousBlog.validation;

import com.alisimsek.HumorousBlog.file.FileService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FileTypeValidator implements ConstraintValidator<FileType,String> {

    private final FileService fileService;

    private String[] types;

    @Override
    public void initialize(FileType constraintAnnotation) {
        this.types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty()) return true;
        String documentType = fileService.detectDocumentType(value);

        for (String validType : types ){
            if (documentType.contains(validType)) return true;
        }

        String validDocumentTypes = Arrays.stream(types).collect(Collectors.joining(", "));
        constraintValidatorContext.disableDefaultConstraintViolation();
        HibernateConstraintValidatorContext hibernateConstraintValidatorContext = constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class);
        hibernateConstraintValidatorContext.addMessageParameter("types", validDocumentTypes);
        hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate()).addConstraintViolation();

        return false;
    }
}
