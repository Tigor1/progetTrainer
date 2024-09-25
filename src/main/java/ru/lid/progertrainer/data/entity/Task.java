package ru.lid.progertrainer.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(schema = "proger_trainer", name = "task")
@SuperBuilder
@Data
@NamedEntityGraph(
        name = "task.users",
        attributeNodes = @NamedAttributeNode("user")
)
public class Task extends BaseEntityWithDateTime {

    private String tittle;

    @Enumerated(EnumType.STRING)
    private TaskDifficultyLevel taskDifficultyLevel;

    private String descriptionOfTask;

    private String solution;

    private Long numberOfSolutions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
