package ru.lid.progertrainer.data.repository;

import org.springframework.data.domain.Pageable;
import ru.lid.progertrainer.dto.request.task.TaskRequestDto;
import ru.lid.progertrainer.dto.response.task.TaskResponseDto;

public interface TaskRepositoryCustom {
    CustomPage<TaskResponseDto> findAllByFilters(TaskRequestDto taskRequestDto, Pageable pageable);
}
