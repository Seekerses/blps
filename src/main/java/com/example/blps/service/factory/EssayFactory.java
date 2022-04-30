package com.example.blps.service.factory;

import com.example.blps.model.referat.Essay;
import com.example.blps.model.referat.dto.EssayUploadDTO;

public interface EssayFactory {
    Essay create(EssayUploadDTO uploadDTO);
}
