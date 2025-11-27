package com.bug_board.bugboard26.enum_classes.converters;

import com.bug_board.bugboard26.enum_classes.IssueState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IssueStateConverter implements AttributeConverter<IssueState, String> {
    @Override
    public String convertToDatabaseColumn(IssueState issueState) {
        if(issueState == null)
            return null;

        return issueState.toString();
    }

    @Override
    public IssueState convertToEntityAttribute(String issueStateString) {
        if(issueStateString == null)
            return null;

        for(IssueState issueState : IssueState.values()) {
            if(issueState.toString().equals(issueStateString))
                return issueState;
        }

        throw new IllegalArgumentException("Unknown issue state: " + issueStateString);
    }
}
