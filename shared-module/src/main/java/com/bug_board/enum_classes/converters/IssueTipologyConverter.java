package com.bug_board.enum_classes.converters;

import com.bug_board.enum_classes.IssueTipology;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IssueTipologyConverter implements AttributeConverter<IssueTipology, String> {

    @Override
    public String convertToDatabaseColumn(IssueTipology issueTipology) {
        if(issueTipology == null)
            return null;

        return issueTipology.toString();
    }

    @Override
    public IssueTipology convertToEntityAttribute(String issueTipologyString) {
        if(issueTipologyString == null)
            return null;

        for(IssueTipology issueType: IssueTipology.values()) {
            if(issueType.toString().equals(issueTipologyString))
                return issueType;
        }

        throw new IllegalArgumentException("Unknown issue type: " + issueTipologyString);
    }
}
