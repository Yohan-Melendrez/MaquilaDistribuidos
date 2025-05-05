/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.conversor.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import service.conversor.dto.ConversionDTO;
import service.conversor.servicio.ConversorServicio;

/**
 *
 * @author USER
 */
@RestController
@RequestMapping("/conversor")
public class ConversorController {

    private final ConversorServicio currencyClientService;

    public ConversorController(ConversorServicio currencyClientService) {
        this.currencyClientService = currencyClientService;
    }

    @GetMapping("/obtener")
    public ResponseEntity<ConversionDTO> obtenerConversion(@RequestParam String from, @RequestParam String to) {
        ConversionDTO dto = currencyClientService.getConversion(from, to);
        return ResponseEntity.ok(dto);
    }
}
