/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 *
 * @author dennisschmock
 */
public class PasswordHandler {

    StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

    public static void main(String[] args) {
        PasswordHandler pwdh = new PasswordHandler();
        String myPassword = "mypassword";
        String encPassword = pwdh.encryptPassword(myPassword);
        System.out.println(myPassword + " Becomes: " + encPassword);
        System.out.println("Password checks out?: " + pwdh.checkPassword(myPassword, encPassword));
    }

    public String encryptPassword(String pwd) {
        String encryptedPassword = passwordEncryptor.encryptPassword(pwd);
        return encryptedPassword;
    }

    public boolean checkPassword(String inputPwd, String encpwd) {
        return passwordEncryptor.checkPassword(inputPwd, encpwd);

    }
}
