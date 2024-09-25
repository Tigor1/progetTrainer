package ru.lid.progertrainer.dto.request.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.lid.progertrainer.data.entity.TaskDifficultyLevel;
import ru.lid.progertrainer.data.exeption.EnumValidator;

@Data
@Schema(name = "Новая таска")
public class NewTaskRequestDto {
    @Schema(description = "Заголовок такси")
    @NotEmpty(message = "tittle is mandatory")
    private String tittle;

    @Schema(description = "Уровень сложности")
    @NotEmpty(message = "taskDifficultyLevel is mandatory")
    @EnumValidator(enumClass = TaskDifficultyLevel.class)
    private String taskDifficultyLevel;

    @Schema(description = "Описание таски")
    @NotEmpty(message = "descriptionOfTask is mandatory")
    private String descriptionOfTask;
}
