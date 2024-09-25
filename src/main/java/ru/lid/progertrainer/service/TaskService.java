package ru.lid.progertrainer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.lid.progertrainer.data.entity.Task;
import ru.lid.progertrainer.data.repository.TaskRepository;
import ru.lid.progertrainer.dto.request.task.NewTaskRequestDto;
import ru.lid.progertrainer.dto.response.task.TaskResponseDto;
import ru.lid.progertrainer.dto.response.task.TasksReponseDto;
import ru.lid.progertrainer.mapper.TaskMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskToTaskResponseDtoMapper;
    private static final int PAGE_SIZE = 5;

    public TasksReponseDto getTaskByPage(int page) {
        List<TaskResponseDto> taskResponseDtos = taskRepository.findAll(Pageable.ofSize(PAGE_SIZE).withPage(page))
                .map(taskToTaskResponseDtoMapper::taskToTaskResponseDto)
                .toList();
        long totalPage = Double.valueOf(Math.ceil(taskRepository.count() / PAGE_SIZE) - 1).longValue();

        return TasksReponseDto.builder()
                .dataTask(taskResponseDtos)
                .currentPage(page)
                .totalPage(totalPage)
                .build();
    }

    public TasksReponseDto getTaskByTitle(String search, int page) {
        List<TaskResponseDto> tasks = taskRepository.findByTittleContains(
                        search,
                        PageRequest.of(page, PAGE_SIZE, Sort.by("tittle"))
                ).stream()
                .map(taskToTaskResponseDtoMapper::taskToTaskResponseDto)
                .toList();

        long totalPage = Double.valueOf(Math.ceil(taskRepository.count() / PAGE_SIZE)).longValue();
        return TasksReponseDto.builder()
                .dataTask(tasks)
                .totalPage(totalPage)
                .currentPage(page)
                .build();
    }

    public void addNewTask(NewTaskRequestDto newTaskRequestDto) {
        Task task = taskToTaskResponseDtoMapper.newTaskDtoToTask(newTaskRequestDto);
        taskRepository.save(task);
    }
}
