package ru.lid.progertrainer.dto.response.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.lid.progertrainer.data.entity.Difficulty;

@Data
@AllArgsConstructor
@Schema(name = "Таска")
public class TaskResponseDto {
    @Schema(description = "Идентификатор задачи")
    private Long id;

    @Schema(description = "Заголовок такси")
    private String title;

    @Schema(description = "Уровень сложности")
    private Difficulty difficulty;

    @Schema(description = "Количество решений")
    private Long numberOfSolutions;
}
