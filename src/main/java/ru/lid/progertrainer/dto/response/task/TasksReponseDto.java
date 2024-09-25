package ru.lid.progertrainer.dto.response.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(name = "Таски")
@Builder
public class TasksReponseDto {
    @Schema(description = "все таски")
    List<TaskResponseDto> dataTask;
    @Schema(description = "текущая страница")
    Integer currentPage;
    @Schema(description = "всего страниц")
    Long totalPage;
}
