package org.mowitnow.mower.service;

import org.mowitnow.mower.dto.MowerTasksDto;
import org.mowitnow.mower.mapper.MowerTasksMapper;
import org.mowitnow.mower.models.InstructionEnum;
import org.mowitnow.mower.models.Mower;
import org.mowitnow.mower.models.MowerTask;
import org.mowitnow.mower.persistence.MowerTaskDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class MowerServiceImpl implements MowerTaskService {
    private static final Logger logger = LoggerFactory.getLogger(MowerServiceImpl.class);

    MowerTaskDao mowerTaskDao;

    public MowerServiceImpl(MowerTaskDao mowerTaskDao) {
        this.mowerTaskDao = mowerTaskDao;
    }

    public void executeMowerTask(MowerTask mowerTask) {

        mowerTask.getInstructions().chars()
                .mapToObj(i -> (char) i)
                .map(letter -> InstructionEnum.valueOf(letter.toString()))
                .forEach(instruction -> {
                    processInstructions(mowerTask.getMower(), instruction);
                });
        mowerTask.setFinalPosition(mowerTask.getMower().toString());
        mowerTaskDao.save(mowerTask);
        logger.info(STR."Running - Mower final position for instruction \{mowerTask.getInstructions()} and initial positon \{mowerTask.getInitalPosition()}");
        logger.info(STR."Running - Mower final position \{mowerTask.getMower().toString()}");
    }

    @Override
    public void executeAllMowerTask(MowerTasksDto mowerTasksDto) {
        try {
            // Create a fixed thread pool
            ExecutorService executor = Executors.newFixedThreadPool(Math.min(1, 6));
            List<CompletableFuture<Void>> futures = MowerTasksMapper.fromDtoToMowerTasks(mowerTasksDto)
                    .stream()
                    .map(mowerTask -> CompletableFuture.runAsync(() -> executeMowerTask(mowerTask), executor))
                    .collect(Collectors.toList());
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allOf.get();
            executor.shutdown();

        } catch (InterruptedException | ExecutionException e) {
            logger.error("There was an issue when executing tasks in virtual thread", e);
        }
    }

    @Override
    public List<MowerTask> getAllMowerTask() {
        var mowerTask = mowerTaskDao.findAll();
        return mowerTaskDao.findAll();
    }

    @Override
    public MowerTask getMowerTask(String mowerTaskId) {
        return mowerTaskDao.findById(mowerTaskId).orElseThrow(() -> new NoSuchElementException(STR."Mower with id \{mowerTaskId} didn't exist"));
    }

    private static void processInstructions(Mower mower, InstructionEnum instructionEnum) {
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
