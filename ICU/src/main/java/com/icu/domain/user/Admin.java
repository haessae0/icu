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
    public Admin(String username, String password, String fullname, String phoneNumber, String userImage) {
        super(username, password, fullname, phoneNumber, userImage, "rADMIN");
    }
}
