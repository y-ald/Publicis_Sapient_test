package org.mowItNow.mower.service;

import org.mowItNow.mower.models.InstructionEnum;
import org.mowItNow.mower.models.Mower;
import org.mowItNow.mower.models.MowerTask;
import org.mowItNow.mower.persistence.MowerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class MowerServiceImpl implements MowerService {
    private static Logger logger = LoggerFactory.getLogger(MowerServiceImpl.class);

    MowerDao mowerDao;
    public MowerServiceImpl(MowerDao mowerDao) {
        this.mowerDao = mowerDao;
    }

    @Override
    public void executeMowerTask(Path filePath) {
        List<MowerTask> mowerTasks = mowerDao.getAllMowerTasks(filePath);
        Consumer<MowerTask> runMowerTask = (mowerTask) -> {
           String initialPositions = mowerTask.mower().toString();
            mowerTask.instructions().chars()
                    .mapToObj(i -> (char) i)
                    .map(letter -> InstructionEnum.valueOf(letter.toString()))
                    .forEach(instruction -> {
                        processInstructions(mowerTask.mower(), instruction);
                    });
            System.out.println();
            System.out.println(STR."Config file \{filePath.getFileName()} - Mower final position for instruction \{mowerTask.instructions()} and initial positon \{initialPositions}");
            System.out.println(STR."Mower final position \{mowerTask.mower().toString()}");
        };
        mowerTasks.forEach(runMowerTask);

        // save results
        String content = mowerTasks.stream()
                .map(MowerTask::mower)
                .map(Mower::toString)
                .collect(Collectors.joining("\n"));
        mowerDao.save(content, filePath.getFileName());
    }

    @Override
    public void executeAllMowerTask(List<Path> filesPath) {
        try {
            // Create a fixed thread pool
            ExecutorService executor = Executors.newFixedThreadPool(Math.min(filesPath.size(), 10));

            List<CompletableFuture<Void>> futures = filesPath.stream()
                    .map(path -> CompletableFuture.runAsync(() -> executeMowerTask(path), executor))
                    .collect(Collectors.toList());
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOf.get();
            executor.shutdown();

        } catch (InterruptedException | ExecutionException e) {
            logger.error("There was an issue when executing tasks in virtual thread", e);
        }
    }

    private void processInstructions(Mower mower, InstructionEnum instructionEnum) {
            switch (instructionEnum) {
                case D:
                    mower.rotateRight();
                    break;
                case G:
                    mower.rotateLeft();
                    break;
                case A:
                    mower.moveForward();
                    break;
                case B:
                    mower.moveBackward();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid instruction: " + instructionEnum);
            }
    }

}
