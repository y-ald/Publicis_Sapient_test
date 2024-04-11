package org.mowitnow.mower.service;

import org.mowitnow.mower.models.Ground;
import org.mowitnow.mower.models.Mower;
import org.mowitnow.mower.models.MowerTask;
import org.mowitnow.mower.models.Orientation;

import java.util.List;

public class DBUtils {
    public static List<MowerTask> getMowerTasks() {
        Ground ground = new Ground(5, 5);
        Mower mower1 = Mower.builder()
                .ground(ground)
                .orientation(Orientation.valueOf("N"))
                .xPosition(1)
                .yPosition(3)
                .build();
        Mower mower2 = Mower.builder()
                .ground(ground)
                .orientation(Orientation.valueOf("E"))
                .xPosition(5)
                .yPosition(1)
                .build();
        MowerTask mowerTask1 = MowerTask.builder()
                .id("1")
                .mower(mower1)
                .initalPosition("1 2 N")
                .finalPosition("1 3 N")
                .instructions("GAGAGAGAA")
                .build();
        MowerTask mowerTask2 = MowerTask.builder()
                .id("2")
                .mower(mower2)
                .initalPosition("3 3 E")
                .finalPosition("5 1 E")
                .instructions("AADAADADDA")
                .build();
        return List.of(mowerTask1, mowerTask2);
    }
}
