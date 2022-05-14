package ru.laboratory.blps.model.user.dto;

import lombok.Builder;
import lombok.Data;
import ru.laboratory.blps.model.user.User;
import ru.laboratory.blps.model.user.UserRole;

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
