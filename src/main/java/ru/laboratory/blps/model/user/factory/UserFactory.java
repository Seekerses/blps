package ru.laboratory.blps.model.user.factory;

import ru.laboratory.blps.model.user.User;
import ru.laboratory.blps.model.user.dto.UserRegisterDTO;

public interface UserFactory {
    User createUser(UserRegisterDTO registerDTO);
    User createAdmin(UserRegisterDTO registerDTO);
}
