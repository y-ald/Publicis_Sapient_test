package org.mowItNow.mower.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mowItNow.mower.models.Mower;
import org.mowItNow.mower.models.MowerTask;
import org.mowItNow.mower.models.Orientation;
import org.mowItNow.mower.persistence.MowerDao;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@SpringJUnitConfig
public class MowerServiceImplTest {
    @MockBean
    MowerDao mowerDaoMock;

    MowerService mowerService;

    List<MowerTask> mowerTasks;
    Mower mower1;
    Mower mower2;
    Mower mower3;

    @BeforeEach
    void setup() {
        mower1 = new Mower(
            UUID.randomUUID(),
                1,
                2,
                Orientation.N,
                5,
                5
        );
        mower2 = new Mower(
            UUID.randomUUID(),
                3,
                3,
                Orientation.E,
                5,
                5
        );
        mower3 = new Mower(
                UUID.randomUUID(),
                1,
                1,
                Orientation.E,
                2,
                2
        );
        mowerTasks = List.of(
                new MowerTask(mower1, "GAGAGAGAA"),
                new MowerTask(mower2, "AADAADADDA")
        );

        mowerService = new MowerServiceImpl(mowerDaoMock);
    }

    @Test
    @DisplayName("Given mowerTasks should call save with mower final position")
    void executeMowerTask_Given_mowerTasks_should_call_save_with_mower_final_position(){
        // GIVEN
        Mockito.when(mowerDaoMock.getAllMowerTasks(Mockito.any())).thenReturn(mowerTasks);
        Path path = Paths.get("test.txt");
        // WHEN
        mowerService.executeMowerTask(path);

        ArgumentCaptor<String> contentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Path> pathCaptor = ArgumentCaptor.forClass(Path.class);
        verify(mowerDaoMock).save(contentCaptor.capture(), pathCaptor.capture());
        String contentCaptured = contentCaptor.getValue();

        // THEN
        Assertions.assertThat(contentCaptured).isEqualTo("1 3 N\n5 1 E");
    }

    @Test
    @DisplayName("Given empty mowerTask should call save with empty string")
    void executeMowerTask_Given_empty_mowerTasks_should_call_save_with_empty_string(){
        // GIVEN
        Mockito.when(mowerDaoMock.getAllMowerTasks(Mockito.any())).thenReturn(List.of());
        Path path = Paths.get("test.txt");
        // WHEN
        mowerService.executeMowerTask(path);

        ArgumentCaptor<String> contentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Path> pathCaptor = ArgumentCaptor.forClass(Path.class);
        verify(mowerDaoMock).save(contentCaptor.capture(), pathCaptor.capture());
        String contentCaptured = contentCaptor.getValue();

        // THEN
        Assertions.assertThat(contentCaptured).isEqualTo("");
    }

    @Test
    @DisplayName("Given mowerTask with wrong instruction should throw IllegalArgumentException")
    void executeMowerTask_Given_mowerg_Tasks_with_wrong_instruction_should_throw_IllegalArgumentException(){
        Mockito.when(mowerDaoMock.getAllMowerTasks(Mockito.any())).thenReturn(List.of(new MowerTask(mower3, "ZZZZZZZZZZ")));
        Path path = Paths.get("test.txt");

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                . isThrownBy( () -> mowerService.executeMowerTask(path));
    }

    @Test
    @DisplayName("Given mowerTask with incoherent instruction should throw RuntimeException")
    void executeMowerTask_Given_mowerg_Tasks_with_non_coherent_instruction_should_throw_RuntimeException(){
        Mockito.when(mowerDaoMock.getAllMowerTasks(Mockito.any())).thenReturn(List.of(new MowerTask(mower3, "AAAAAAAAAAAA")));
        Path path = Paths.get("test.txt");

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                . isThrownBy( () -> mowerService.executeMowerTask(path));
    }
}
