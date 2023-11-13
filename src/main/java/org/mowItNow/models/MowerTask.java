package org.mowItNow.models;

public record MowerTask(
        Mower mower,
        String instructions
) { }
