package ru.laboratory.blps.model.essay.factory;

import ru.laboratory.blps.model.essay.Essay;
import ru.laboratory.blps.model.essay.dto.EssayUploadDTO;

public interface EssayFactory {
    Essay create(EssayUploadDTO uploadDTO);
}
