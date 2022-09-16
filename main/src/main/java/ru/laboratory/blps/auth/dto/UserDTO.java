package ru.laboratory.blps.auth.dto;

import ru.laboratory.blps.auth.User;
import ru.laboratory.blps.auth.UserRole;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {

    private long id;
    private String username;
    private UserRole role;

    public static UserDTO create(User user){
        return UserDTO.builder()
                .username(user.getUsername())
                .id(user.getId())
                .role(user.getRole())
                .build();
    }
}
