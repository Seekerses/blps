package ru.laboratory.blps.auth.factory;

import ru.laboratory.blps.auth.dto.UserRegisterDTO;
import ru.laboratory.blps.auth.Credentials;

public interface CredentialsFactory {
    Credentials create(UserRegisterDTO registerDTO);
}
