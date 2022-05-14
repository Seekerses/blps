package ru.laboratory.blps.model.essay.factory;

import ru.laboratory.blps.model.essay.Comment;
import ru.laboratory.blps.model.essay.dto.CommentCreateDTO;
import ru.laboratory.blps.model.user.User;

public interface CommentFactory {
    Comment create(CommentCreateDTO createDTO, User user);
}
