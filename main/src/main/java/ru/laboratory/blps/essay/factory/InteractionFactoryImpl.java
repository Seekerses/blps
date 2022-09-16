package ru.laboratory.blps.essay.factory;

import ru.laboratory.blps.essay.Interaction;
import ru.laboratory.blps.essay.Mark;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InteractionFactoryImpl implements InteractionFactory {

    private final MarkFactory markFactory;

    @Override
    public Interaction create() {
        List<Mark> marks = new ArrayList<>();
        marks.add(markFactory.create(1));
        marks.add(markFactory.create(2));
        marks.add(markFactory.create(3));
        marks.add(markFactory.create(4));
        marks.add(markFactory.create(5));

        return Interaction.builder()
                .comments(new ArrayList<>())
                .marks(marks)
                .totalMark(0)
                .views(0)
                .build();
    }
}
