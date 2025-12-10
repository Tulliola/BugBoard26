package com.bug_board.enum_classes.converters;

import com.bug_board.enum_classes.IssuePriority;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IssuePriorityConverter implements AttributeConverter<IssuePriority, String> {
    @Override
    public String convertToDatabaseColumn(IssuePriority issuePriority) {
        if(issuePriority == null)
            return null;

        return issuePriority.toString();
    }

    @Override
    public IssuePriority convertToEntityAttribute(String issuePriorityString) {
        if(issuePriorityString == null)
            return null;

        for(IssuePriority issuePriority : IssuePriority.values()) {
            if(issuePriority.toString().equals(issuePriorityString))
                return issuePriority;
        }

        throw new IllegalArgumentException("Unknown issue priority: " + issuePriorityString);
    }
}