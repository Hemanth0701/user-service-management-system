package com.tenjiku.userservice.mapper;

import com.tenjiku.userservice.dto.exit_dto.user_registeration.AdminResponseDTO;
import com.tenjiku.userservice.dto.exit_dto.user_registeration.DoctorResponseDTO;
import com.tenjiku.userservice.dto.exit_dto.user_registeration.UserDetailsResponseDTO;
import com.tenjiku.userservice.dto.exit_dto.user_registeration.UserResponseDTO;
import com.tenjiku.userservice.entity.Admin;
import com.tenjiku.userservice.entity.Doctor;
import com.tenjiku.userservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsExitMapper {

    public UserDetailsResponseDTO toAdminDTO(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        AdminResponseDTO adminResponseDTO = new AdminResponseDTO();
        adminResponseDTO.setId(admin.getId());
        adminResponseDTO.setUsername(admin.getUsername());
        adminResponseDTO.setEmail(admin.getEmail());
        adminResponseDTO.setPhoneNumber(admin.getPhoneNumber());
        adminResponseDTO.setDob(admin.getDob());
        adminResponseDTO.setRole(String.valueOf(admin.getRole()));
        adminResponseDTO.setCreatedAt(admin.getCreatedAt());

        return adminResponseDTO;
    }

    public UserDetailsResponseDTO toUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhoneNumber(user.getPhoneNumber());
        userResponseDTO.setDob(user.getDob());
        userResponseDTO.setRole(String.valueOf(user.getRole()));
        userResponseDTO.setCreatedAt(user.getCreatedAt());

        return userResponseDTO;
    }

    public UserDetailsResponseDTO toDoctorDTO(Doctor doctor) {
        if ( doctor == null ) {
            return null;
        }

       DoctorResponseDTO doctorResponseDTO= new DoctorResponseDTO();

        doctorResponseDTO.setId(doctor.getId());
        doctorResponseDTO.setUsername(doctor.getUsername());
        doctorResponseDTO.setEmail(doctor.getEmail());
        doctorResponseDTO.setPhoneNumber(doctor.getPhoneNumber());
        doctorResponseDTO.setDob(doctor.getDob());
        doctorResponseDTO.setRole(String.valueOf(doctor.getRole()));
        doctorResponseDTO.setQualification(doctor.getQualification()!=null? doctor.getQualification() : " Update Qualification");
        doctorResponseDTO.setRegNo(doctor.getRegNo()!=null? doctor.getRegNo() : " Update RegNo");
        doctorResponseDTO.setSpeciality(doctor.getSpeciality()!=null? doctor.getSpeciality() : " Update Speciality");

        return doctorResponseDTO;
    }
}
