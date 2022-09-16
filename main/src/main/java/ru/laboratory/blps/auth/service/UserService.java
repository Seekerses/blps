package ru.laboratory.blps.auth.service;

import ru.laboratory.blps.auth.User;
import ru.laboratory.blps.auth.dto.UpdatePasswordDTO;
import ru.laboratory.blps.auth.dto.UserRegisterDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.laboratory.blps.auth.dto.UpdateUsernameDTO;
import ru.laboratory.blps.auth.exceptions.UserNotFound;

public interface UserService extends UserDetailsService {

    void createUser(UserRegisterDTO registerDTO);
    void createAdmin(UserRegisterDTO registerDTO);
    void changeUsername(UpdateUsernameDTO updateUsernameDTO, Long userId) throws UserNotFound;
    void changePassword(UpdatePasswordDTO updatePasswordDTO, Long userId) throws UserNotFound;
    User getById(Long userId) throws UserNotFound;
}
