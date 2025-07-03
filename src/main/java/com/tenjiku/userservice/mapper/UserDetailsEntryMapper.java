package com.tenjiku.userservice.mapper;

import com.tenjiku.userservice.dto.entry_dto.user_registeration.AdminDTO;
import com.tenjiku.userservice.dto.entry_dto.user_registeration.DoctorDTO;
import com.tenjiku.userservice.dto.entry_dto.user_registeration.UserDTO;
import com.tenjiku.userservice.entity.Admin;
import com.tenjiku.userservice.entity.Doctor;
import com.tenjiku.userservice.entity.User;
import com.tenjiku.userservice.entity.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsEntryMapper {

    public Admin toAdmin(AdminDTO adminDTO) {
        if ( adminDTO == null ) {
            return null;
        }

        Admin admin =new Admin();
        admin.setUsername(adminDTO.getUsername());
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(adminDTO.getPassword());
        admin.setPhoneNumber(adminDTO.getPhoneNumber());
        admin.setDob(adminDTO.getDob());
        admin.setRole(Role.ADMIN);

        return admin;
    }

    public User toUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setDob(userDTO.getDob());
        user.setRole(Role.USER);
        return user;
    }

    public Doctor toDoctor(DoctorDTO doctorDTO) {
        if ( doctorDTO == null ) {
            return null;
        }

        Doctor doctor= new Doctor();
        doctor.setUsername(doctorDTO.getUsername());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setPassword(doctorDTO.getPassword());
        doctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        doctor.setDob(doctorDTO.getDob());
        doctor.setRole(Role.DOCTOR);
        return doctor;
    }

}
