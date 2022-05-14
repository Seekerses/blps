package ru.laboratory.blps.model.user.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.laboratory.blps.model.user.Credentials;
import ru.laboratory.blps.model.user.dto.UserRegisterDTO;

@RequiredArgsConstructor
@Service
public class CredentialsFactoryImpl implements CredentialsFactory {

    private final PasswordEncoder passwordEncoder;

    @Override
    public Credentials create(UserRegisterDTO registerDTO) {
        return Credentials.builder()
                .isExpired(false)
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .username(registerDTO.getUsername())
                .build();
    }
}
