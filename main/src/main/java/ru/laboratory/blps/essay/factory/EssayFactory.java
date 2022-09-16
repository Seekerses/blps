package ru.laboratory.blps.essay.factory;

import ru.laboratory.blps.essay.Essay;
import ru.laboratory.blps.essay.dto.EssayUploadDTO;

public interface EssayFactory {
    Essay create(EssayUploadDTO uploadDTO);
}
