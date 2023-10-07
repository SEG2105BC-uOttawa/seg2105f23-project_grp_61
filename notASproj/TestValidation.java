import java.util.regex.Pattern;
import java.util.regex.Matcher;

class TestValidation {
  public static void main(String[] args){
    String email = "g61f23seg2105c@example.com";
    String pass = "}l92$2,FL(qV";

    String badEmail = "notan.email";
    String badPass = null;

    System.out.println("Email " + email + " " + validateEmailWithRegex(email));
    System.out.println("Password " + pass + " " + validatePass(pass));
    System.out.println("Email " + badEmail + " " + validateEmailWithRegex(badEmail));
    System.out.println("Password " + badPass + " " + validatePass(badPass));
  }

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
}
