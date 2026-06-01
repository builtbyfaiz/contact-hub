package service;

/** The class providing input validation using REGEX */
public class Validator {

    /*
     Email rules:
     atleast one char before @
     atleast one char after @
     Must contain dot in the domain part
    */
    public static boolean isEmail(String email) {
        return email != null && email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }

    /*
     Mobile rules:
     Prefix: +92, 92, 0
     - then 3
     - then 9 digits
     */
    public static boolean isPhone(String phone) {
        return phone != null && phone.matches("^(?:\\+92|92|0)3\\d{9}$");
    }
}