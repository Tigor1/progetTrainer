package ru.lid.progertrainer.common.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.lid.progertrainer.data.entity.Difficulty;

@Component
public class DifficultyConverter implements Converter<String, Difficulty> {

    @Override
    public Difficulty convert(String source) {
        if (source == null || source.isEmpty()) {
            return null; // или выбросить исключение
        }

        // Преобразование строки в верхний регистр
        return Difficulty.valueOf(source.toUpperCase());
    }
}