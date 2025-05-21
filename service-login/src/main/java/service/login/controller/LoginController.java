/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.login.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import service.login.servicio.AuthService;


/**
 *
 * @author abelc
 */
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials, @RequestParam String departamento) {
        String token = authService.login(credentials.get("nombre"), credentials.get("contrasena"), departamento);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
