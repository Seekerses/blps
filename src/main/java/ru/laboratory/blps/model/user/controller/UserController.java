package ru.laboratory.blps.model.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.laboratory.blps.model.user.Token;
import ru.laboratory.blps.model.user.dto.LoginDTO;
import ru.laboratory.blps.model.user.dto.UpdatePasswordDTO;
import ru.laboratory.blps.model.user.dto.UpdateUsernameDTO;
import ru.laboratory.blps.model.user.dto.UserRegisterDTO;
import ru.laboratory.blps.model.user.exceptions.AccountSuspended;
import ru.laboratory.blps.model.user.exceptions.UserNotFound;
import ru.laboratory.blps.model.user.exceptions.WrongPassword;
import ru.laboratory.blps.model.user.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserRegisterDTO registerDTO){
        userService.createUser(registerDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin")
    public ResponseEntity<Void> createAdmin(@RequestBody UserRegisterDTO registerDTO){
        userService.createAdmin(registerDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/username")
    public ResponseEntity<Void> changeUsername(@RequestBody UpdateUsernameDTO updateUsernameDTO, HttpServletRequest request){
        String userId = (String) request.getAttribute("UserId");
        userService.changeUsername(updateUsernameDTO, Long.valueOf(userId));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void>  changePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO, HttpServletRequest request){
        String userId = (String) request.getAttribute("UserId");
        userService.changePassword(updatePasswordDTO, Long.valueOf(userId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Token> signIn(@RequestBody LoginDTO loginDTO) throws AccountSuspended, WrongPassword, UserNotFound {
        return ResponseEntity.ok(userService.signIn(loginDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody String refreshToken){
        userService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Token> refresh(@RequestBody String refreshToken){
        return ResponseEntity.ok(userService.refresh(refreshToken));
    }
}
