/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package service.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author abelc
 */
public class testcryp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
          BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123456";
        String hash = encoder.encode(rawPassword);
        System.out.println("Hash: " + hash);
        System.out.println("Coincide? " + encoder.matches(rawPassword, hash));
    }
    
}
