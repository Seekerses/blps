package ru.laboratory.blps.auth.controller;

import ru.laboratory.blps.auth.dto.LoginDTO;
import ru.laboratory.blps.auth.dto.UpdatePasswordDTO;
import ru.laboratory.blps.auth.dto.UpdateUsernameDTO;
import ru.laboratory.blps.auth.dto.UserRegisterDTO;
import ru.laboratory.blps.auth.exceptions.AccountSuspended;
import ru.laboratory.blps.auth.exceptions.UserNotFound;
import ru.laboratory.blps.auth.exceptions.WrongPassword;
import ru.laboratory.blps.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAnyAuthority( \"admin\")")
    @PostMapping("/admin")
    public ResponseEntity<Void> createAdmin(@RequestBody UserRegisterDTO registerDTO){
        userService.createAdmin(registerDTO);
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PreAuthorize("hasAnyAuthority(\"user\", \"admin\")")
    @PutMapping("/username")
    public ResponseEntity<Void> changeUsername(@RequestBody UpdateUsernameDTO updateUsernameDTO, HttpServletRequest request){
        String userId = (String) request.getAttribute("UserId");
        userService.changeUsername(updateUsernameDTO, Long.valueOf(userId));
        return ResponseEntity.ok().build();
    }

    @SneakyThrows
    @PreAuthorize("hasAnyAuthority(\"user\", \"admin\")")
    @PutMapping("/password")
    public ResponseEntity<Void>  changePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO, HttpServletRequest request){
        String userId = (String) request.getAttribute("UserId");
        userService.changePassword(updatePasswordDTO, Long.valueOf(userId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> signIn(@RequestBody LoginDTO loginDTO) throws AccountSuspended, WrongPassword, UserNotFound {
        return ResponseEntity.ok().build();
    }
}
