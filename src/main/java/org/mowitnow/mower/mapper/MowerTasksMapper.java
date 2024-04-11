package org.mowitnow.mower.mapper;

import org.mowitnow.mower.dto.MowerTasksDto;
import org.mowitnow.mower.models.Ground;
import org.mowitnow.mower.models.Mower;
import org.mowitnow.mower.models.MowerTask;
import org.mowitnow.mower.models.Orientation;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MowerTasksMapper {
    public static List<MowerTask> fromDtoToMowerTasks(MowerTasksDto mowerTasksDto) {
        List<Integer> lawnSize = getLawnSize(mowerTasksDto.getGround());
        AtomicInteger taskNumber = new AtomicInteger(0);
        return mowerTasksDto.getMowerAndInstructions().stream().map(mowerAndInstruction -> {
            String[] mowerPositions = mowerAndInstruction.split(";")[0].split(" ");
            String mowerInstruction = mowerAndInstruction.split(";")[1];
            Mower mower = Mower.builder()
                    .ground(new Ground(lawnSize.get(0), lawnSize.get(1)))
                    .orientation(Orientation.valueOf(mowerPositions[2]))
                    .xPosition(Integer.parseInt(mowerPositions[0]))
                    .yPosition(Integer.parseInt(mowerPositions[1]))
                    .build();
            return MowerTask.builder()
                    .id(mowerTasksDto.getIds().get(taskNumber.getAndIncrement()))
                    .mower(mower)
                    .initalPosition(mower.toString())
                    .instructions(mowerInstruction)
                    .build();
        }).collect(Collectors.toList());
    }

    private static List<Integer> getLawnSize(String line) {
        return Arrays.stream(line.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
