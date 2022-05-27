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
    @Column(name = "user_id", length = 25)
    private String username;

    @JsonIgnore
    @Column(name = "user_pw", length = 200)
    private String password;

    @Column(name = "user_name", length = 200)
    private String fullname;

    @Column(name = "user_phonenum", length = 200)
    private String phoneNumber;

    @Column(name = "role", nullable = false, insertable = false, updatable = false)
    private String role;
}
