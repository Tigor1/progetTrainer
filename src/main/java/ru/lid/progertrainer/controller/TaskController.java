package ru.lid.progertrainer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.lid.progertrainer.dto.request.task.NewTaskRequestDto;
import ru.lid.progertrainer.dto.response.task.TasksReponseDto;
import ru.lid.progertrainer.service.TaskService;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Задачи", description = "Сервис для работы с тасками")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    @Operation(description = "получить все таски постранично")
    public TasksReponseDto getTasks(@RequestParam(required = false) int page, @RequestParam(required = false) String search) {
        if (Objects.isNull(search)) {
            return taskService.getTaskByPage(page);
        }
        return taskService.getTaskByTitle(search, page);
    }

    @PostMapping
    public String addTask(@RequestBody NewTaskRequestDto newTaskRequestDto) {
        taskService.addNewTask(newTaskRequestDto);
        return "OK";
    }
}
