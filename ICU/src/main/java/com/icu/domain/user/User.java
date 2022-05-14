package com.icu.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
public class User {

    @Id
    @Column(name = "u_id", length = 25)
    private String userId;

    @JsonIgnore
    @Column(name = "u_password", length = 200)
    private String password;

    @Column(name = "u_name", length = 200)
    private String name;

    @Column(name = "u_phonenumber", length = 200)
    private String phoneNumber;

    @Column(name = "u_profile_image")
    private String profileImage;

    @Column(name = "role", nullable = false, insertable = false, updatable = false)
    private String role;
}
