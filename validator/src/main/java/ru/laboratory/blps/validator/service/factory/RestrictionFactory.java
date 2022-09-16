package ru.laboratory.blps.validator.service.factory;

import ru.laboratory.blps.validator.Restriction;
import ru.laboratory.blps.validator.dto.RestrictionDTO;

public interface RestrictionFactory {
    Restriction create(RestrictionDTO restriction);
}
