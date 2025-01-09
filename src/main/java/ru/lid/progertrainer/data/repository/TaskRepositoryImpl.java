package ru.lid.progertrainer.data.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.lid.progertrainer.data.entity.Task;
import ru.lid.progertrainer.dto.request.task.TaskRequestDto;
import ru.lid.progertrainer.dto.response.task.TaskResponseDto;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CustomPage<TaskResponseDto> findAllByFilters(TaskRequestDto taskRequestDto, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Основной запрос с проекцией
        CriteriaQuery<TaskResponseDto> query = cb.createQuery(TaskResponseDto.class);
        Root<Task> task = query.from(Task.class);

        // Фильтры
        List<Predicate> predicates = buildPredicates(taskRequestDto, cb, task);
        query.where(predicates.toArray(new Predicate[0]));

        // Проекция в TaskResponseDto
        query.select(cb.construct(TaskResponseDto.class,
                task.get("id"),
                task.get("title"),
                task.get("difficulty"),
                task.get("numberOfSolutions")));

        // Сортировка
        if (!pageable.getSort().isEmpty()) {
            query.orderBy(buildSortOrders(pageable, cb, task));
        }

        // Выполнение основного запроса
        TypedQuery<TaskResponseDto> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        List<TaskResponseDto> taskResponseDtos = typedQuery.getResultList();

        // Подсчет общего количества записей
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(task)).where(predicates.toArray(new Predicate[0]));
        Long totalElements = entityManager.createQuery(countQuery).getSingleResult();

        // Формирование CustomPage
        int totalPages = (int) Math.ceil((double) totalElements / pageable.getPageSize());
        return new CustomPage<TaskResponseDto>(
                taskResponseDtos,
                totalPages,
                totalElements,
                pageable.getPageSize(),
                pageable.getPageNumber()
        );
    }

    private List<Predicate> buildPredicates(TaskRequestDto taskRequestDto, CriteriaBuilder cb, Root<Task> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (taskRequestDto.getDifficulty() != null) {
            predicates.add(cb.equal(root.get("difficulty"), taskRequestDto.getDifficulty()));
        }
        if (taskRequestDto.getTitle() != null) {
            predicates.add(cb.like(root.get("tittle"), "%" + taskRequestDto.getTitle() + "%"));
        }
        if (taskRequestDto.getNumberOfSolutions() != null) {
            predicates.add(cb.equal(root.get("numberOfSolutions"), taskRequestDto.getNumberOfSolutions()));
        }
        return predicates;
    }

    private List<Order> buildSortOrders(Pageable pageable, CriteriaBuilder cb, Root<Task> root) {
        List<Order> orders = new ArrayList<>();
        pageable.getSort().forEach(order -> {
            Path<Object> path = root.get(order.getProperty());
            orders.add(order.isAscending() ? cb.asc(path) : cb.desc(path));
        });
        return orders;
    }
}
