package ru.laboratory.blps.model.essay.factory;

import ru.laboratory.blps.model.essay.Mark;
import org.springframework.stereotype.Service;

@Service
public class MarkFactoryImpl implements MarkFactory {
    @Override
    public Mark create(int mark) {
        return Mark.builder()
                .value(mark)
                .count(0)
                .build();
    }
}
