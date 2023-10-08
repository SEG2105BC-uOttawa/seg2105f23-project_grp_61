package com.grimpeurs.cycling;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
/*
*
* Purpose of this class is to create an object that implements Serializable interface to send user data between activities.
*
* Constructor accepts FirebaseUser object.
*
 */
public class AppUser implements Serializable {

    private String email;

    public  AppUser (FirebaseUser user) {

        this.email = user.getEmail();

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
