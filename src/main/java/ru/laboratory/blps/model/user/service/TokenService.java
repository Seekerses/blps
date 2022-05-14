package ru.laboratory.blps.model.user.service;

import ru.laboratory.blps.model.user.Token;
import ru.laboratory.blps.model.user.User;

public interface TokenService {
    Token createToken(User user);
    void deleteToken(String refreshToken);
}
