package com.tenjiku.userservice.dto.entry_dto.user_registeration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PasswordDTO {
    @NotBlank(message = "userId is required")
    private String id;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters, include one uppercase letter, one digit, and one special character"
    )
    private String password;
}
