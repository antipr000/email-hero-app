package com.example.emailhero.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailTemplateProcessor {
    private final Logger logger = LoggerFactory.getLogger(EmailTemplateProcessor.class);
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{\\{([^{}]+)}}");

    public boolean validTemplate(String template, Class<?> targetClass) {
        Matcher matcher = VARIABLE_PATTERN.matcher(template);

        while (matcher.find()) {
            String variableName = matcher.group(1);
            logger.info("Matching var name {}", variableName);

            if (!isValidVariable(variableName, targetClass)) {
                return false;
            }
        }

        return true;
    }

    public String applyTemplate(String template, Object object) {
        Matcher matcher = VARIABLE_PATTERN.matcher(template);
        StringBuilder result = new StringBuilder();

        // Replace each matched variable with the corresponding object property
        while (matcher.find()) {
            String variable = matcher.group(1); // Extract variable name
            String replacement = getPropertyValue(variable, object);
            matcher.appendReplacement(result, replacement != null ? Matcher.quoteReplacement(replacement) : "");
        }
        matcher.appendTail(result);

        return result.toString();
    }

    private String getPropertyValue(String variable, Object object) {
        try {
            String capitalizedVariable = Character.toUpperCase(variable.charAt(0)) + variable.substring(1);
            Object value = object.getClass().getMethod("get" + capitalizedVariable).invoke(object);
            return value != null ? value.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isValidVariable(String variableName, Class<?> targetClass) {
        Field []fields = targetClass.getDeclaredFields();
        logger.info("Declared fields: {}", Arrays.toString(fields));
        for(Field field: fields) {
            logger.info("Matching {} to {}", field.getName(), variableName);
            if (field.getName().equals(variableName)) {
                return true;
            }
        }

        return false;
    }
}
