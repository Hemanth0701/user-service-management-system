package com.tenjiku.userservice.dto.exit_dto.user_registeration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDTO {
    private String token;
    private UserDetailsResponseDTO userDetailsResponse ;
}
