package ru.laboratory.blps.auth.factory;

import ru.laboratory.blps.auth.User;
import ru.laboratory.blps.auth.dto.UserRegisterDTO;

public interface UserFactory {
    User createUser(UserRegisterDTO registerDTO);
    User createAdmin(UserRegisterDTO registerDTO);
}
