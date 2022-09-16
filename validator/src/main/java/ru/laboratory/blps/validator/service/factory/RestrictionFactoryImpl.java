package ru.laboratory.blps.validator.service.factory;

import org.springframework.stereotype.Component;
import ru.laboratory.blps.validator.Restriction;
import ru.laboratory.blps.validator.dto.RestrictionDTO;

@Component
public class RestrictionFactoryImpl implements RestrictionFactory {
    @Override
    public Restriction create(RestrictionDTO restriction) {
        return Restriction.builder()
                .word(restriction.getWord())
                .weight(restriction.getWeight())
                .build();
    }
}
