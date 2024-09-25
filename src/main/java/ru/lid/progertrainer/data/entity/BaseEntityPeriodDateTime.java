package ru.lid.progertrainer.data.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEntityPeriodDateTime extends BaseEntityWithDateTime {

    @Column(name = "start", columnDefinition = "Дата и время начала действия записи")
    protected OffsetDateTime start;

    @Column(name = "end", columnDefinition = "Дата и время окончания действия записи")
    protected OffsetDateTime end;
}
