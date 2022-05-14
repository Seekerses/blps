package ru.laboratory.blps.model.user.factory;

import ru.laboratory.blps.model.user.Credentials;
import ru.laboratory.blps.model.user.dto.UserRegisterDTO;

public interface CredentialsFactory {
    Credentials create(UserRegisterDTO registerDTO);
}
