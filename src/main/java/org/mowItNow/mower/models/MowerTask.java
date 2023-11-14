package org.mowItNow.mower.models;

public record MowerTask(
        Mower mower,
        String instructions
) {
    @Override
    public Mower mower() {
        return mower;
    }

    @Override
    public String instructions() {
        return instructions;
    }
}
