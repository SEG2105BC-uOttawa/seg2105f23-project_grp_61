package com.example.grimpeurscyclingclubtest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextInputValidation {

    /**
     *
     * @param str string to be validated
     * @return true if str is not "" or null
     */
    public static boolean validateString(String str){
        return str != null && str != "";
    }
    /**
     *
     * @param password string of password to be validated
     * @return true if the password is minimum 8 characters length, at least 1 upper and lowercase letter, and 1 number
     */
    public static boolean validatePass(String password){// to be used for unit testing later, as well as all "boolean" functions that don't need to be booleans
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

    /**
     *
     * @param phoneNumber string of user phone number input
     * @return true if the input string matches the regex pattern ^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$
     */
    public static boolean validatePhoneNumberWithRegex(String phoneNumber){
        if (phoneNumber == null){
            return false;
        }

        Pattern pattern = Pattern.compile("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$");
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.find();
    }

    public static boolean validateWorkingHours(String input){
        if (input == null){
            return false;
        }

        Pattern pattern = Pattern.compile("^(?:[01]?\\d|2[0-3])(?:[0-5]\\d){1,2}-(?:[01]?\\d|2[0-3])(?:[0-5]\\d){1,2}$");//0000-2359
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }

    public static boolean validateSocialMedia(String input){
        if(input == null){
            return false;
        }
        Pattern pattern = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()!@:%_\\+.~#?&\\/\\/=]*)");
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }


    public static boolean validateRegistrationFee(String input){
        if (input == null){
            return false;
        }

        Pattern pattern = Pattern.compile("^\\d{1,3}(\\.\\d{1,2})?$");// 0-999.99
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }

    public static boolean validateParticipantLimit(String input){
        if (input == null){
            return false;
        }

        Pattern pattern = Pattern.compile("^([1-9][0-9]{0,2}|1000)$");//1-1000
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }
    public static boolean validate24hrTime(String input){
        if (input == null){
            return false;
        }

        Pattern pattern = Pattern.compile("^(?:[01]?\\d|2[0-3])(?:[0-5]\\d){1,2}$");//0000-2359
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }
    public static boolean validateDate(String input){
        if (input == null){
            return false;
        }
        //matches DD/M/YYYY
        Pattern pattern = Pattern.compile("(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})");//0000-2359
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }

}
