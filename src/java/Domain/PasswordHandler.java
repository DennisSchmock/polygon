/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 *
 * @author dennisschmock
 */
public class PasswordHandler {

    StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

    /**
     *
     * @param pwd
     * @return
     */
    public String encryptPassword(String pwd) {
        String encryptedPassword = passwordEncryptor.encryptPassword(pwd);
        return encryptedPassword;
    }

    /**
     *
     * @param inputPwd
     * @param encpwd
     * @return
     */
    public boolean checkPassword(String inputPwd, String encpwd) {
        return passwordEncryptor.checkPassword(inputPwd, encpwd);

    }
}
