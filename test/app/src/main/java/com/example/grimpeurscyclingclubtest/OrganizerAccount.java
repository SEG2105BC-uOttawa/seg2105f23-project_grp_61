package com.example.grimpeurscyclingclubtest;

public class OrganizerAccount extends Account{
    public String logoImage;

    public OrganizerAccount(String username) {
        super(username);
    }
    public void addLogoImage(String newLogo){
        logoImage = newLogo;
    }
}