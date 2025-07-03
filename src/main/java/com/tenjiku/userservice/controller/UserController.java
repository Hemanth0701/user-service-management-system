package com.tenjiku.userservice.controller;

import com.tenjiku.userservice.dto.entry_dto.user_registeration.PasswordDTO;
import com.tenjiku.userservice.dto.exit_dto.user_registeration.UserDetailsResponseDTO;
import com.tenjiku.userservice.dto.update_dto.user_registeration.DoctorUpdateDTO;
import com.tenjiku.userservice.dto.update_dto.user_registeration.UserDetailsUpdateDTO;
import com.tenjiku.userservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PutMapping(value = "/update/{id}")
   // @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable @NotNull String id,
                                        @Valid @RequestBody UserDetailsUpdateDTO userDetailsUpdateDTO){
        UserDetailsResponseDTO updatedUser= userService.updateUser(id,userDetailsUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }
    @PutMapping(value = "/update/doctor/{id}")
    // @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> updateUser(@PathVariable @NotNull String id,
                                        @Valid @RequestBody DoctorUpdateDTO doctorUpdateDTO){
        UserDetailsResponseDTO updatedUser= userService.updateDoctor(id,doctorUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping(value = "/forgotPassword")
   // @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> updateUserPassword(@Valid @RequestBody PasswordDTO passwordDTO){
        return ResponseEntity.ok(userService.updatePassword(passwordDTO));
    }

    @DeleteMapping(value = "/delete/{id}")
   // @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
