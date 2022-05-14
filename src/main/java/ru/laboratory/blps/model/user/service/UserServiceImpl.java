package ru.laboratory.blps.model.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.laboratory.blps.model.user.Token;
import ru.laboratory.blps.model.user.User;
import ru.laboratory.blps.model.user.dto.LoginDTO;
import ru.laboratory.blps.model.user.dto.UpdatePasswordDTO;
import ru.laboratory.blps.model.user.dto.UpdateUsernameDTO;
import ru.laboratory.blps.model.user.dto.UserRegisterDTO;
import ru.laboratory.blps.model.user.exceptions.AccountSuspended;
import ru.laboratory.blps.model.user.exceptions.UserNotFound;
import ru.laboratory.blps.model.user.exceptions.WrongPassword;
import ru.laboratory.blps.model.user.factory.UserFactory;
import ru.laboratory.blps.model.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final TokenService tokenService;
    private final UserFactory userFactory;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.getUserById(Long.valueOf(username)).orElseThrow(()
                -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    @Override
    public void createUser(UserRegisterDTO registerDTO) {
        User user = userFactory.createUser(registerDTO);
        repository.saveAndFlush(user);
    }

    @Override
    public void createAdmin(UserRegisterDTO registerDTO) {
        User user = userFactory.createAdmin(registerDTO);
        repository.saveAndFlush(user);
    }

    @Override
    public void changeUsername(UpdateUsernameDTO updateUsernameDTO, Long userId) {
        User user = repository.getById(userId);
        user.getCredentials().setUsername(updateUsernameDTO.getUsername());
        repository.saveAndFlush(user);
    }

    @Override
    public void changePassword(UpdatePasswordDTO updatePasswordDTO, Long userId) {
        User user = repository.getById(userId);
        if (passwordEncoder.matches(updatePasswordDTO.getOldPassword(), user.getCredentials().getPassword())){
            user.getCredentials().setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        }
        repository.saveAndFlush(user);
    }

    @Override
    public Token signIn(LoginDTO loginDTO) throws AccountSuspended, WrongPassword, UserNotFound {
        User user = repository.getUserByCredentials_Username(loginDTO.getUsername())
                .orElseThrow(UserNotFound::new);
        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
            if (!user.isExpired() &&
                    user.isAccountNonExpired() &&
                    user.isAccountNonLocked() &&
                    user.isCredentialsNonExpired()){
                Token token = tokenService.createToken(user);
                user.getToken().add(token);
                repository.saveAndFlush(user);
                return token;
            }
            else throw new AccountSuspended();
        }
        else throw new WrongPassword();
    }

    @Override
    public void logout(String refreshToken) {
        tokenService.deleteToken(refreshToken);
    }

    @Override
    public User getById(Long userId) throws UserNotFound {
        return repository.getUserById(userId).orElseThrow(UserNotFound::new);
    }

    @Override
    public Token refresh(String refreshToken) {
        return null;
    }
}
