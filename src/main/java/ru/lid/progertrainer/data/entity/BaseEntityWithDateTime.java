package ru.lid.progertrainer.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;


@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class BaseEntityWithDateTime extends BaseEntity {

    @Column(name = "create_datetime", nullable = false, updatable = false, columnDefinition = "Дата и время создания записи")
    @CreatedDate
    protected OffsetDateTime createDateTime;

    @Column(name = "update_datetime", nullable = false, updatable = false, columnDefinition = "Дата и время изменения записи")
    @UpdateTimestamp
    protected OffsetDateTime updateDateTime;
}
