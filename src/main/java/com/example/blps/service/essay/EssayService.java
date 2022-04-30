package com.example.blps.service.essay;

import com.example.blps.model.referat.Essay;
import com.example.blps.model.referat.dto.CommentCreateDTO;
import com.example.blps.model.referat.dto.EssayUpdateDTO;
import com.example.blps.model.referat.dto.EssayUploadDTO;

public interface EssayService {
    void createEssay(EssayUploadDTO uploadDTO);

    Essay getEssay(long id);

    void deleteEssay(long id);

    Essay updateEssay(EssayUpdateDTO updateDTO);

    void commentEssay(long essayId, CommentCreateDTO createDTO);

    void markEssay(long essayId, int mark);
}
