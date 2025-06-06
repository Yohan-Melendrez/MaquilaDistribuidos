/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.login.servicio;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import service.login.modelo.Usuario;
import service.login.repositorio.UsuarioRepositorio;
/**
 *
 * @author abelc
 */
@Service
public class AuthService {

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // clave secreta de al menos 32 caracteres
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor("clave-secreta-super-segura-1234567890".getBytes());

    public String login(String nombre, String contrasena, String origenSistema) {
        Usuario usuario = usuarioRepo.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (!usuario.getDepartamento().equalsIgnoreCase(origenSistema)) {
            throw new RuntimeException("Acceso denegado al sistema: " + origenSistema);
        }


        return Jwts.builder()
                .setSubject(usuario.getNombre())
                .claim("departamento", usuario.getDepartamento())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}
