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
public class BcryptTest {
    public static void main(String[] args) {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String hash = encoder.encode("admin");
    System.out.println(hash);
    }
    }

