package ru.lid.progertrainer.data.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.lid.progertrainer.data.entity.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, TaskRepositoryCustom, JpaSpecificationExecutor<Task> {

    @EntityGraph(value = "task.users", type = EntityGraph.EntityGraphType.FETCH)
    List<Task> findByTitleContains(String title, Pageable pageable);
}
