package com.tenjiku.userservice.service;

import com.tenjiku.userservice.dto.entry_dto.user_registeration.LoginRequestDTO;
import com.tenjiku.userservice.dto.entry_dto.user_registeration.PasswordDTO;
import com.tenjiku.userservice.dto.entry_dto.user_registeration.UserDetailsDTO;
import com.tenjiku.userservice.dto.exit_dto.user_registeration.LoginResponseDTO;
import com.tenjiku.userservice.dto.exit_dto.user_registeration.UserDetailsResponseDTO;
import com.tenjiku.userservice.dto.update_dto.user_registeration.DoctorUpdateDTO;
import com.tenjiku.userservice.dto.update_dto.user_registeration.UserDetailsUpdateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    LoginResponseDTO login(LoginRequestDTO loginRequest);

    LoginResponseDTO register(@Valid UserDetailsDTO userDetailsDTO);

    UserDetailsResponseDTO updateUser(@NotNull String id, @Valid UserDetailsUpdateDTO userDetailsUpdateDTO);

    UserDetailsResponseDTO updateDoctor(@NotNull String id, @Valid DoctorUpdateDTO doctorUpdateDTO);

    String updatePassword(@Valid PasswordDTO passwordDTO);

    String deleteUser(String id);
}
