/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author LENOVO
 */
public class AttributeValide {
    static Pattern emailPattern = Pattern.compile("[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+");
public static boolean EmailVerified(String email) 
{       
    Matcher match = emailPattern.matcher(email);
        return match.matches();
}
    public static boolean verifierNumeroTel(String num) 
    { 
        Pattern p = Pattern.compile("([0-9]){8}"); 
 
        Matcher m = p.matcher(num); 
        return (m.find() && m.group().equals(num)); 
    }
        public static boolean verifierNumeroCin(String num) 
    { 
        Pattern p = Pattern.compile("([0-9]){8}"); 
 
        Matcher m = p.matcher(num); 
        return (m.find() && m.group().equals(num)); 
    }
    
}
