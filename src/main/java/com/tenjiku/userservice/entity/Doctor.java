package com.tenjiku.userservice.entity;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Entity
@Setter
@Getter
public class Doctor extends UserInfo{

    private String qualification;
    private String speciality;
    private String RegNo;

}
