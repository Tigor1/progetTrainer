package ru.lid.progertrainer.data.repository;

import jakarta.persistence.criteria.Predicate;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;
import ru.lid.progertrainer.data.entity.Task;
import ru.lid.progertrainer.data.entity.Task_;
import ru.lid.progertrainer.dto.request.task.TaskRequestDto;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {
    public static Specification<Task> getSpecification(TaskRequestDto taskRequestDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (taskRequestDto.getDifficulty() != null) {
                predicates.add(criteriaBuilder.equal(root.get(Task_.DIFFICULTY), taskRequestDto.getDifficulty().name()));
            }

            if (Strings.isNotEmpty(taskRequestDto.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get(Task_.TITLE), "%" + taskRequestDto.getTitle() + "%"));
            }

            if (taskRequestDto.getNumberOfSolutions() != null) {
                predicates.add(criteriaBuilder.equal(root.get(Task_.numberOfSolutions), taskRequestDto.getNumberOfSolutions()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
