package ru.laboratory.blps.model.user.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.laboratory.blps.model.user.Credentials;
import ru.laboratory.blps.model.user.User;
import ru.laboratory.blps.model.user.UserRole;
import ru.laboratory.blps.model.user.dto.UserRegisterDTO;

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
