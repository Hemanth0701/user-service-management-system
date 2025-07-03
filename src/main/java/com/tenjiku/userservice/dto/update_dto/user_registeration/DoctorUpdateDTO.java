package com.tenjiku.userservice.dto.update_dto.user_registeration;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;
@Getter
public class DoctorUpdateDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "Phone number must be valid (10–15 digits, optional +)"
    )
    private String phoneNumber;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotBlank(message = "qualification is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String qualification;

    @NotBlank(message = "speciality is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String speciality;

    @NotBlank(message = "RegNo is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String RegNo;
}
