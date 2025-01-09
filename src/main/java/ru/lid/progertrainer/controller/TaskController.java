package ru.lid.progertrainer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lid.progertrainer.data.exeption.ErrorDetails;
import ru.lid.progertrainer.dto.request.task.NewTaskRequestDto;
import ru.lid.progertrainer.dto.request.task.TaskRequestDto;
import ru.lid.progertrainer.dto.response.task.TaskResponseDto;
import ru.lid.progertrainer.service.TaskService;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Задачи", description = "Сервис для работы с тасками")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @Operation(description = "получить все задачи постранично с фильтром и сортипровкой")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "получить все задачи постранично с фильтром и сортипровкой"),
            @ApiResponse(responseCode = "400", description = "Неправильный формат запроса к сервису", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "сервер выполнил некорректную операцию/произошла непредвиденная ошибка", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetails.class))),
    })
    public Page<TaskResponseDto> getTasks(@RequestBody TaskRequestDto taskRequestDto) {
        return taskService.getTask(taskRequestDto);
    }

    @PostMapping("/add")
    public String addTask(@RequestBody NewTaskRequestDto newTaskRequestDto) {
        taskService.addNewTask(newTaskRequestDto);
        return "OK";
    }
}
