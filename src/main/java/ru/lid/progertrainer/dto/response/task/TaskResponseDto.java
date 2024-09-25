package ru.lid.progertrainer.dto.response.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Таска")
public class TaskResponseDto {
    @Schema(description = "Заголовок такси")
    private String tittle;

    @Schema(description = "Уровень сложности")
    private String taskDifficultyLevel;

    @Schema(description = "Количество решений")
    private int numberOfSolutions;
}
