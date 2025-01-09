package ru.lid.progertrainer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.lid.progertrainer.data.entity.Task;
import ru.lid.progertrainer.data.repository.TaskRepository;
import ru.lid.progertrainer.data.repository.TaskSpecification;
import ru.lid.progertrainer.dto.request.task.NewTaskRequestDto;
import ru.lid.progertrainer.dto.request.task.TaskRequestDto;
import ru.lid.progertrainer.dto.response.task.TaskResponseDto;
import ru.lid.progertrainer.mapper.TaskMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private static final int PAGE_SIZE = 5;

    public Page<TaskResponseDto> getTask(TaskRequestDto taskRequestDto) {
        Pageable pageable = PageRequest.of(
                taskRequestDto.getPage(),
                taskRequestDto.getSize()
        );

        Specification<Task> specification = TaskSpecification.getSpecification(taskRequestDto);

        try {
            return taskRepository.findAll(specification, pageable)
                    .map(taskMapper::taskToTaskResponseDto);
        } catch (Exception e) {
            log.error("Error during get tasks with request: {}, {}", taskRequestDto, pageable, e);
            throw e;
        }
    }

    public void addNewTask(NewTaskRequestDto newTaskRequestDto) {
        Task task = taskMapper.newTaskDtoToTask(newTaskRequestDto);
        taskRepository.save(task);
    }
}
