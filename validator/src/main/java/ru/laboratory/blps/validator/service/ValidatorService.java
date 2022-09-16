package ru.laboratory.blps.validator.service;

import ru.laboratory.blps.validator.Restriction;
import ru.laboratory.blps.validator.dto.RestrictionDTO;

public interface ValidatorService {
    boolean validate(String text);
    Restriction addRestriction(RestrictionDTO restriction);
    void deleteRestriction(Long restrictionID);
    Restriction updateRestriction(RestrictionDTO restriction);
}
