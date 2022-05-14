package ru.laboratory.blps.model.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.laboratory.blps.model.user.Token;
import ru.laboratory.blps.model.user.User;
import ru.laboratory.blps.model.user.repository.TokenRepository;
import ru.laboratory.blps.utils.JwtService;
import ru.laboratory.blps.utils.TokenType;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public Token createToken(User user) {
        Token token = new Token();
        token.setAccessToken(jwtService.generateToken(user, TokenType.access));
        token.setRefreshToken(jwtService.generateToken(user, TokenType.refresh));
        return token;
    }

    @Override
    @Transactional
    public void deleteToken(String refreshToken) {
        Token token = tokenRepository.getTokenByRefreshToken(refreshToken)
                .orElseThrow(IllegalArgumentException::new);
        tokenRepository.deleteFromMergeTable(token.getId());
        tokenRepository.delete(
                tokenRepository.getTokenByRefreshToken(refreshToken)
                        .orElseThrow(IllegalArgumentException::new));
    }
}
