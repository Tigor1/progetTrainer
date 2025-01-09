package ru.lid.progertrainer.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
public class Topic3Dto {
    private final List<Integer> numbers;
}

