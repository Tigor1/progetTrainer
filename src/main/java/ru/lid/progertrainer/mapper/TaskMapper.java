package ru.lid.progertrainer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.lid.progertrainer.data.entity.Task;
import ru.lid.progertrainer.data.entity.TaskDifficultyLevel;
import ru.lid.progertrainer.dto.request.task.NewTaskRequestDto;
import ru.lid.progertrainer.dto.response.task.TaskResponseDto;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "taskDifficultyLevel", source = "taskDifficultyLevel", qualifiedByName = "taskDifficultyLevelToString")
    TaskResponseDto taskToTaskResponseDto(Task task);

    @Mapping(target = "createDateTime", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "updateDateTime", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "taskDifficultyLevel", source = "taskDifficultyLevel", qualifiedByName = "StringTotaskDifficultyLevel")
    @Mapping(target = "solution", constant = "default_solution")
    @Mapping(target = "numberOfSolutions", constant = "0L")
    Task newTaskDtoToTask(NewTaskRequestDto newTaskRequestDto);

    @Named("taskDifficultyLevelToString")
    default String taskDifficultyLevelToString(TaskDifficultyLevel taskDifficultyLevel) {
        return taskDifficultyLevel.name();
    }

    @Named("StringTotaskDifficultyLevel")
    default TaskDifficultyLevel stringTotaskDifficultyLevel(String taskDifficultyLevel) {
        return TaskDifficultyLevel.valueOf(taskDifficultyLevel);
    }
}
