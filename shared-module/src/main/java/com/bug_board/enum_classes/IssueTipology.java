package com.bug_board.enum_classes;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.IOException;

public enum IssueTipology {
    Bug("Bug", "/shared_module/images/bug.png"),
    NewFeature("NewFeature", "/shared_module/images/new_feature.png"),
    Question("Question", "/shared_module/images/question.png"),
    Documentation("Documentation", "/shared_module/images/documentation.png");

    private final String tipology;
    private final String imageURL;

    IssueTipology(String tipology, String imageURL)
    {
        this.imageURL = imageURL;
        this.tipology = tipology;
    }

    public byte[] getAssociatedImage() {
        try {
            return IssueTipology.class.getResourceAsStream(this.imageURL).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonValue
    public String getTipology() {
        return this.tipology;
    }

    @Override
    public String toString() {
        return this.tipology;
    }
}
