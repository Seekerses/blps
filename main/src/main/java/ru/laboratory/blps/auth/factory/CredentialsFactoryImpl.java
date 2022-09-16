package ru.laboratory.blps.auth.factory;

import ru.laboratory.blps.auth.dto.UserRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.laboratory.blps.auth.Credentials;

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
