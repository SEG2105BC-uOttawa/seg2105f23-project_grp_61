package com.example.grimpeurscyclingclubtest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextInputValidation {
    /**
     *
     * @param password string of password to be validated
     * @return true if the password is minimum 8 characters length, at least 1 upper and lowercase letter, and 1 number
     */
    public static boolean validatePass(String password){
        if(password == null){
            return false;
        }

        boolean longEnough = false;
        boolean oneUpper = false;
        boolean oneLower = false;
        boolean oneNumber = false;

        longEnough = password.length() >=8;
        if (!longEnough){
            System.out.println("Too Short");
            return false;
        }


        for(int i = 0; i < password.length(); i++){
            if(!oneUpper && Character.isUpperCase(password.charAt(i))){
                oneUpper = true;
            }
            if(!oneLower && Character.isLowerCase(password.charAt(i))){
                oneLower = true;
            }
            if(!oneNumber && "1234567890".contains(("" + password.charAt(i)))){
                oneNumber = true;
            }

            if(longEnough && oneUpper && oneLower && oneNumber){
                return true;
            }
        }

        boolean result = longEnough && oneUpper && oneLower && oneNumber;
        if(result){
            return result;
        }
        else{
            // System.out.println("longEnough " + longEnough);
            // System.out.println("oneUpper " + oneUpper);
            // System.out.println("oneLower " + oneLower);
            // System.out.println("oneNumber " + oneNumber);
            return result;
        }
    }


    /**
     *
     * @param emailAddress string of email address to be validated
     * @return true if emailAddress follows the appropriate pattern
     */
    public static boolean validateEmailWithRegex(String emailAddress){

        if(emailAddress == null){
            return false;
        }

        Pattern pattern = Pattern.compile("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$");
        Matcher matcher = pattern.matcher(emailAddress);

        return matcher.find();
    }

    public static boolean validateUsernameWithRegex(String username){
        if (username == null){
            return false;
        }

        Pattern pattern = Pattern.compile("^\\w+");
        Matcher matcher = pattern.matcher(username);

        return matcher.find();
    }
}