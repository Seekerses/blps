package com.example.blps.service.factory;

import com.example.blps.model.referat.Comment;
import com.example.blps.model.referat.dto.CommentCreateDTO;

public interface CommentFactory {
    Comment create(CommentCreateDTO createDTO);
}
