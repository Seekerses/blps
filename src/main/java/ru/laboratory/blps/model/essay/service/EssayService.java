package ru.laboratory.blps.model.essay.service;

import ru.laboratory.blps.model.essay.Essay;
import ru.laboratory.blps.model.essay.dto.CommentCreateDTO;
import ru.laboratory.blps.model.essay.dto.EssayUpdateDTO;
import ru.laboratory.blps.model.essay.dto.EssayUploadDTO;
import ru.laboratory.blps.model.user.exceptions.UserNotFound;

import java.util.List;

public interface EssayService {
    void createEssay(EssayUploadDTO uploadDTO);

    Essay getEssay(long id);

    void deleteEssay(long id);

    Essay updateEssay(EssayUpdateDTO updateDTO);

    void commentEssay(long essayId, CommentCreateDTO createDTO, Long userId) throws UserNotFound;

    void markEssay(long essayId, int mark);

    List<Essay> getByCategory(String category);

    List<Essay> getAll();

    List<Essay> searchByPhrase(String phrase);
}
