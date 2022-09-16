package ru.laboratory.blps.auth.factory;

import ru.laboratory.blps.auth.User;
import ru.laboratory.blps.auth.dto.UserRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.laboratory.blps.auth.Credentials;
import ru.laboratory.blps.auth.UserRole;

@RequiredArgsConstructor
@Service
public class UserFactoryImpl implements UserFactory {

    private final CredentialsFactory credentialsFactory;

    @Override
    public User createUser(UserRegisterDTO registerDTO) {
        Credentials credentials = credentialsFactory.create(registerDTO);

        return User.builder()
                .credentials(credentials)
                .isEnabled(true)
                .isExpired(false)
                .isLocked(false)
                .role(UserRole.user)
                .build();
    }

    @Override
    public User createAdmin(UserRegisterDTO registerDTO) {
        Credentials credentials = credentialsFactory.create(registerDTO);

        return User.builder()
                .credentials(credentials)
                .isEnabled(true)
                .isExpired(false)
                .isLocked(false)
                .role(UserRole.admin)
                .build();
    }
}
