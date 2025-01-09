package ru.lid.progertrainer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.lid.progertrainer.data.entity.Difficulty;
import ru.lid.progertrainer.data.entity.Task;
import ru.lid.progertrainer.dto.request.task.NewTaskRequestDto;
import ru.lid.progertrainer.dto.response.task.TaskResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "difficulty", source = "difficulty", qualifiedByName = "difficultyToString")
    TaskResponseDto taskToTaskResponseDto(Task task);

    List<TaskResponseDto> taskListToTaskResponseDtoList(List<Task> tasks);

    @Mapping(target = "createDateTime", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "updateDateTime", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "difficulty", source = "difficulty", qualifiedByName = "StringTotaskDifficultyLevel")
    @Mapping(target = "solution", constant = "default_solution")
    @Mapping(target = "numberOfSolutions", constant = "0L")
    Task newTaskDtoToTask(NewTaskRequestDto newTaskRequestDto);

    @Named("difficultyToString")
    default String taskDifficultyLevelToString(Difficulty taskDifficultyLevel) {
        return taskDifficultyLevel.name();
    }

    @Named("StringTotaskDifficultyLevel")
    default Difficulty stringTotaskDifficultyLevel(String taskDifficultyLevel) {
        return Difficulty.valueOf(taskDifficultyLevel);
    }
}
