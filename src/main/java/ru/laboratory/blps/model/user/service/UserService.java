package ru.laboratory.blps.model.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.laboratory.blps.model.user.Token;
import ru.laboratory.blps.model.user.User;
import ru.laboratory.blps.model.user.dto.LoginDTO;
import ru.laboratory.blps.model.user.dto.UpdatePasswordDTO;
import ru.laboratory.blps.model.user.dto.UpdateUsernameDTO;
import ru.laboratory.blps.model.user.dto.UserRegisterDTO;
import ru.laboratory.blps.model.user.exceptions.AccountSuspended;
import ru.laboratory.blps.model.user.exceptions.UserNotFound;
import ru.laboratory.blps.model.user.exceptions.WrongPassword;

public interface UserService extends UserDetailsService {

    void createUser(UserRegisterDTO registerDTO);
    void createAdmin(UserRegisterDTO registerDTO);
    void changeUsername(UpdateUsernameDTO updateUsernameDTO, Long userId);
    void changePassword(UpdatePasswordDTO updatePasswordDTO, Long userId);
    Token signIn(LoginDTO loginDTO) throws AccountSuspended, WrongPassword, UserNotFound;
    void logout(String refreshToken);
    User getById(Long userId) throws UserNotFound;
    Token refresh(String refreshToken);
}
