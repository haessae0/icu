package com.icu.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("rADMIN")
public class Admin extends User {

    @Builder
    public Admin() {
        super();
    }

    @Builder
    public Admin(String userId, String password, String name, String phoneNumber, String profileImage) {
        super(userId, password, name, phoneNumber, profileImage, "rADMIN");
    }
}
