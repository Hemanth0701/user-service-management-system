package com.tenjiku.userservice.service.Impl;

import com.tenjiku.userservice.dto.entry_dto.user_registeration.*;
import com.tenjiku.userservice.dto.exit_dto.user_registeration.LoginResponseDTO;
import com.tenjiku.userservice.dto.exit_dto.user_registeration.UserDetailsResponseDTO;
import com.tenjiku.userservice.dto.update_dto.user_registeration.AdminUpdateDTO;
import com.tenjiku.userservice.dto.update_dto.user_registeration.DoctorUpdateDTO;
import com.tenjiku.userservice.dto.update_dto.user_registeration.UserDetailsUpdateDTO;
import com.tenjiku.userservice.dto.update_dto.user_registeration.UserUpdateDTO;
import com.tenjiku.userservice.entity.Admin;
import com.tenjiku.userservice.entity.Doctor;
import com.tenjiku.userservice.entity.User;
import com.tenjiku.userservice.entity.UserInfo;
import com.tenjiku.userservice.exception.InternalServerErrorException;
import com.tenjiku.userservice.exception.UserAlreadyDeletedException;
import com.tenjiku.userservice.exception.UserAlreadyExistsException;
import com.tenjiku.userservice.exception.UserNotFoundException;
import com.tenjiku.userservice.mapper.UserDetailsEntryMapper;
import com.tenjiku.userservice.mapper.UserDetailsExitMapper;
import com.tenjiku.userservice.repos.UserRepo;
import com.tenjiku.userservice.security.CustomUserDetails;
import com.tenjiku.userservice.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.tenjiku.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtUtil jwtUtil;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsEntryMapper userDetailsEntryMapper;
    private final UserDetailsExitMapper userDetailsExitMapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getPhoneNumberOrEmail(),
                        loginRequest.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserInfo appUser = userDetails.getUser();

        UserDetailsResponseDTO responseDTO = switch (appUser) {
            case User user -> userDetailsExitMapper.toUserDTO(user);
            case Admin admin -> userDetailsExitMapper.toAdminDTO(admin);
            case Doctor doctor -> userDetailsExitMapper.toDoctorDTO(doctor);
            default -> throw new IllegalStateException("Unexpected user type: " + appUser.getClass().getSimpleName());
        };

        String token = jwtUtil.generateToken(userDetails);

        // Return a combined DTO including token and user details
        return new LoginResponseDTO(token, responseDTO);
    }

    @Override
    public LoginResponseDTO register(UserDetailsDTO userDetailsDTO) {

        //  check if an email or PhoneNumber already exist
        if (userRepo.existsByPhoneNumber(userDetailsDTO.getPhoneNumber()) || userRepo.existsByEmail(userDetailsDTO.getEmail())) {
            throw new UserAlreadyExistsException(" PhoneNumber or email already exists");
        }

        UserInfo savedUser;
        UserDetailsResponseDTO responseDTO;

        switch (userDetailsDTO) {
            case AdminDTO adminDTO -> {
                Admin admin = userDetailsEntryMapper.toAdmin(adminDTO);
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
                savedUser = userRepo.save(admin);
                responseDTO = userDetailsExitMapper.toAdminDTO(admin);
            }
            case UserDTO userDTO -> {
                User user = userDetailsEntryMapper.toUser(userDTO);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                savedUser = userRepo.save(user);
                responseDTO = userDetailsExitMapper.toUserDTO(user);
            }
            case DoctorDTO doctorDTO -> {
                Doctor doctor = userDetailsEntryMapper.toDoctor(doctorDTO);
                doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
                savedUser = userRepo.save(doctor);
                responseDTO = userDetailsExitMapper.toDoctorDTO(doctor);
            }
            default ->
                    throw new InternalServerErrorException("Unexpected user type: " + userDetailsDTO.getClass().getSimpleName());
        }

        CustomUserDetails userDetails = new CustomUserDetails(savedUser);
        String token = jwtUtil.generateToken(userDetails);
        return new LoginResponseDTO(token, responseDTO);
    }

    @Override
    public UserDetailsResponseDTO updateUser(String id, UserDetailsUpdateDTO userDetailsUpdateDTO) {

        UserInfo existingUser = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found With Id : "+id));

        if (userDetailsUpdateDTO instanceof AdminUpdateDTO) {

            return userDetailsExitMapper.toAdminDTO((Admin) userRepo.save(mergeUserDetails(existingUser,userDetailsUpdateDTO)));
        } else if (userDetailsUpdateDTO instanceof UserUpdateDTO) {

            return userDetailsExitMapper.toUserDTO((User) userRepo.save(mergeUserDetails(existingUser,userDetailsUpdateDTO)));
        } else
            throw new InternalServerErrorException(" Server Went Down ");
    }

    @Override
    public UserDetailsResponseDTO updateDoctor(String id, DoctorUpdateDTO doctorUpdateDTO) {

        Doctor existingdoctor = (Doctor) userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found With Id : "+id));

        return userDetailsExitMapper.toDoctorDTO( (Doctor) userRepo.save(mergeDetails(existingdoctor,doctorUpdateDTO)));
    }

    private Doctor mergeDetails(Doctor existingUser, DoctorUpdateDTO doctorUpdateDTO) {

        // Update fields — only if they're provided (null-safe)
        if (doctorUpdateDTO.getUsername() != null) existingUser.setEmail(doctorUpdateDTO.getUsername());
        if (doctorUpdateDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(doctorUpdateDTO.getPhoneNumber());
        if (doctorUpdateDTO.getDob() != null) existingUser.setDob(doctorUpdateDTO.getDob());
        if (doctorUpdateDTO.getQualification() != null)  existingUser.setQualification(doctorUpdateDTO.getQualification());
        if (doctorUpdateDTO.getSpeciality() != null) existingUser.setSpeciality(doctorUpdateDTO.getSpeciality());
        if (doctorUpdateDTO.getRegNo() != null) existingUser.setRegNo(doctorUpdateDTO.getRegNo());

        return existingUser;

    }

    public UserInfo mergeUserDetails(UserInfo existingUser, UserDetailsUpdateDTO userDetailsUpdateDTO){

        if(existingUser instanceof Admin & userDetailsUpdateDTO instanceof AdminUpdateDTO){
            // Update fields — only if they're provided (null-safe)
            if (userDetailsUpdateDTO.getUsername() != null) existingUser.setEmail(userDetailsUpdateDTO.getUsername());
            if (userDetailsUpdateDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(userDetailsUpdateDTO.getPhoneNumber());
            if (userDetailsUpdateDTO.getDob() != null) existingUser.setDob(userDetailsUpdateDTO.getDob());
            return existingUser;

        } else if (existingUser instanceof User & userDetailsUpdateDTO instanceof UserUpdateDTO) {
            // Update fields — only if they're provided (null-safe)
            if (userDetailsUpdateDTO.getUsername() != null) existingUser.setEmail(userDetailsUpdateDTO.getUsername());
            if (userDetailsUpdateDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(userDetailsUpdateDTO.getPhoneNumber());
            if (userDetailsUpdateDTO.getDob() != null) existingUser.setDob(userDetailsUpdateDTO.getDob());
            return existingUser;

        }else
            throw new InternalServerErrorException(" Unexpected user type: " + userDetailsUpdateDTO.getClass().getSimpleName());
    }

    @Override
    public String updatePassword(PasswordDTO passwordDTO) {
        // Fetch user by ID
        UserInfo user = userRepo.findById(passwordDTO.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + passwordDTO.getId()));

        // set new password
        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));

        // Save updated user
        userRepo.save(user);

        return " Password Updated successfully " ;
    }

    @Override
    public String deleteUser(String id) {

        UserInfo user = userRepo.findById(id)
                .orElseThrow(() ->  new UserNotFoundException("User not found with ID: " + id));

        if (user.isDeleted())
            throw new UserAlreadyDeletedException("User is already deleted.");

        user.setDeleted(true);
        user.setDeletedAt(Instant.now());
        userRepo.save(user);

        return user.getUsername()+" Account Deleted Successfully ";
    }
}
