package ru.laboratory.blps.essay.factory;

import ru.laboratory.blps.auth.User;
import ru.laboratory.blps.essay.Comment;
import ru.laboratory.blps.essay.dto.CommentCreateDTO;

public interface CommentFactory {
    Comment create(CommentCreateDTO createDTO, User user);
}
