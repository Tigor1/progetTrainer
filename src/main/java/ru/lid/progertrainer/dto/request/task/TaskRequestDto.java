package ru.lid.progertrainer.dto.request.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.lid.progertrainer.data.entity.Difficulty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskRequestDto {
    private Difficulty difficulty;
    private String title;
    private Long numberOfSolutions;
    private int page = 0;
    private int size = 5;
}
