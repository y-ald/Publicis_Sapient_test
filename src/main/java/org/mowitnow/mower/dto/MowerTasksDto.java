package org.mowitnow.mower.dto;

import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class MowerTasksDto {
    private final List<String> ids;
    private final String ground;
    private final List<String> mowerAndInstructions;

    public MowerTasksDto(String ground, List<String> mowerAndInstructions, List<String> ids) {
        this.ground = ground;
        this.mowerAndInstructions = mowerAndInstructions;
        this.ids = ids.isEmpty() ? mowerAndInstructions.stream().map(e -> UUID.randomUUID().toString()).toList() : ids;
    }
}
