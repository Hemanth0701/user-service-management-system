package com.tenjiku.userservice.dto.exit_dto.user_registeration;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DoctorResponseDTO extends UserDetailsResponseDTO {
    private String qualification;
    private String speciality;
    private String RegNo;
}
